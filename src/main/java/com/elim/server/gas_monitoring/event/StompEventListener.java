package com.elim.server.gas_monitoring.event;

import com.elim.server.gas_monitoring.service.SensorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.*;


@Slf4j
@Component
@RequiredArgsConstructor
public class StompEventListener {

    // 일단은 싱글 스레드 - 포트/센서가 많아지면 ThreadPool 로 바꾸는게 좋을수도
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final SimpMessagingTemplate messagingTemplate;
    private final SensorService sensorService;

    // 세션 ID -> (sensorName:port) 매핑 (예: "UA58-KFG-U:COM3:25090199")
    private final Map<String, String> sessionSubscriptions = new ConcurrentHashMap<>();

    // subscriptionKey -> 스케줄러 매핑
    private final Map<String, ScheduledFuture<?>> taskMap = new ConcurrentHashMap<>();


    /**
     * 클라이언트가 STOMP 구독을 시도할 때 호출
     * <p>기대 destination 형식: /topic/sensor/{model}/{port}/{serialNumber}
     * 예) /topic/sensor/UA58-KFG-U/COM3/25090199 </p>
     */
    @EventListener
    public void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        String destination = accessor.getDestination();

        if (!isSensorDestination(destination)) { // sensor 토픽만 처리
            log.warn("잘못된 토픽={}", destination);
            return;
        }

        String[] parts = destination.split("/");
        if(!isValidDestination(parts)) { // destination 제대로 왔는지 확인
            log.warn("잘못된 destination 형식: {} (센서명/포트/시리얼 누락)", destination);
            return;
        }

        String model = parts[3];
        String port = parts[4];
        String serialNumber = parts[5];

        if (!isValidModel(model)) { // 센서명 유효한지 확인
            log.warn("잘못된 센서명 구독 시도: {}", model);
            return;
        }

        String key = registerSubscription(model, port, serialNumber, sessionId); // 구독

        startSchedulerIfAbsent(key, model, port, serialNumber); // 스케줄러가 없으면 새로 생성 후 시작
    }

    private boolean isSensorDestination(String destination) {
        return destination != null && destination.startsWith("/topic/sensor/");
    }

    private boolean isValidDestination(String[] parts) {
        // 최소 길이 검증 (sensorName, port, serialNumber 다 있어야 함)
        return parts.length >= 6;
    }

    private boolean isValidModel(String model) {
        return model.matches("UA58-KFG-U|UA58-LEL");
    }

    private String registerSubscription(
            String model,
            String port,
            String serialNumber,
            String sessionId
    ) {
        // 세션 <-> 구독키(센서명:포트) 매핑 저장
        String key = model + ":" + port + ":" + serialNumber; // 예: UA58-KFG-U:COM3:25090199
        sessionSubscriptions.put(sessionId, key);

        // 현재 동일 key(같은 센서/포트)를 구독 중인 세션 수 집계
        long subscriberCount = sessionSubscriptions.values().stream()
                .filter(v -> v.equals(key))
                .count();

        log.info("구독자 추가 sessionId={}, model={}, port={}, 현재 구독자 수={}", sessionId, model, port, subscriberCount);
        return key;
    }

    private void startSchedulerIfAbsent(String key, String model, String port, String serialNumber) {
        taskMap.computeIfAbsent(key, k ->
                scheduler.scheduleAtFixedRate(() -> {
                    publishSensorData(model, port, serialNumber, k); // 센서 데이터 전달
                }, 0, 2, TimeUnit.SECONDS)
        );
    }

    private void publishSensorData(String model, String port, String serialNumber, String key) {
        try {
            // 최신 구독자 수 계산
            long liveSubscribers = sessionSubscriptions.values().stream()
                    .filter(v -> v.equals(key))
                    .count();

            // 센서 데이터 Object에 담은 후에 응답 보냄
            Object dto;
            switch (model) {
                case "UA58-KFG-U" -> dto = sensorService.readValuesFromKFGU(port, model, serialNumber);
                case "UA58-LEL" -> dto = sensorService.readValuesFromLEL(port, model, serialNumber);
                default -> dto = "지원하지 않는 센서명입니다: " + model;
            }

            // 구독자에게 메세지 전달
            messagingTemplate.convertAndSend("/topic/sensor/" + model + "/" + port + "/" + serialNumber, dto);
        } catch (Exception e) {
            log.error("Sensor read error key={}", key, e);
        }
    }


    /**
     * 특정 세션이 unsubscribe 했을 때 호출
     * */
    @EventListener
    public void handleSessionUnsubscribeEvent(SessionUnsubscribeEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        removeSession(sessionId);
    }


    /**
     * 특정 세션이 소켓을 끊었을 때 호출
     * */
    @EventListener
    public void handleSessionDisconnectEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        removeSession(sessionId);
    }

    /**
     * 세션 종료/해제 시: 세션-구독키 매핑을 제거하고,
     * 동일 key 의 구독자가 하나도 남지 않으면 해당 key의 스케줄러를 중단/정리
     */
    private void removeSession(String sessionId) {
        String subscriptionKey = sessionSubscriptions.remove(sessionId);
        if (subscriptionKey != null) {
            log.info("❌ 세션 종료 sessionId={}, key={}", sessionId, subscriptionKey);

            // 동일 key 로 구독 중인 세션이 남아있는지 확인
            boolean stillSubscribed = sessionSubscriptions.containsValue(subscriptionKey);

            // 더 이상 없으면 스케줄러 중단 및 핸들 제거
            if (!stillSubscribed) {
                ScheduledFuture<?> task = this.taskMap.remove(subscriptionKey);
                if (task != null) {
                    task.cancel(false); // 인터럽트 없이 취소 — 실행 중이던 read 는 1번 더 돌 수 있음
                }

                String[] parts = subscriptionKey.split(":");
            }
        }
    }
}
