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

    // 세션 ID -> (sensorName:port) 매핑 (예: "UA58KFGU:COM3")
    private final Map<String, String> sessionSubscriptions = new ConcurrentHashMap<>();

    // subscriptionKey -> 스케줄러 매핑
    private final Map<String, ScheduledFuture<?>> taskMap = new ConcurrentHashMap<>();


    /**
     * 클라이언트가 STOMP 구독을 시도할 때 호출
     * <p>기대 destination 형식: /topic/sensor/{sensorName}/{port}  예) /topic/sensor/UA58KFGU/COM3 </p>
     */
    @EventListener
    public void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        String destination = accessor.getDestination();

        // sensor 토픽만 처리
        if (destination != null && destination.startsWith("/topic/sensor/")) {

            // /topic/sensor/UA58KFGU/COM3 → ["", "topic", "sensor", "UA58KFGU", "COM3"]
            String[] parts = destination.split("/");

            String sensorName = parts[3]; // 센서 종류 추출, 예) UA58KFGU
            String port = parts[4]; // 포트 번호 추출, 예) COM3

            // 센서명 검증 (whitelist)
            if (!sensorName.matches("UA58KFG|UA58LEL")) {
                log.warn("잘못된 센서명 구독 시도: {}", sensorName);
                return;
            }

            // 세션 ↔ 구독키(센서명:포트) 매핑 저장
            String key = sensorName + ":" + port; // 예: UA58KFGU:COM3
            sessionSubscriptions.put(sessionId, key);

            // 현재 동일 key(=같은 센서/포트)를 구독 중인 세션 수 집계
            long subscriberCount = sessionSubscriptions.values().stream()
                    .filter(v -> v.equals(key))
                    .count();

//            log.info("구독자 추가 sessionId={}, port={}", sessionId, port);
            log.info("구독자 추가 sessionId={}, sensorName={}, port={}, 현재 구독자 수={}", sessionId, sensorName, port, subscriberCount);


            // 스케줄러가 없으면 새로 생성
            taskMap.computeIfAbsent(key, k ->
                    scheduler.scheduleAtFixedRate(() -> {
                        try {
                            // 최신 구독자 수 계산
                            long liveSubscribers = sessionSubscriptions.values().stream()
                                    .filter(v -> v.equals(k))
                                    .count();

                            Object dto;
                            switch (sensorName) {
                                case "UA58KFGU" -> dto = sensorService.readValuesFromKFG(port);
                                case "UA58LEL" -> dto = sensorService.readValuesFromLEL(port);
                                default -> dto = "지원하지 않는 센서명입니다: " + sensorName;
                            }

                            messagingTemplate.convertAndSend("/topic/sensor/" + sensorName + "/" + port, dto);
                            log.info("key={}, 구독자수={}, data={}", k, liveSubscribers, dto);

                        } catch (Exception e) {
                            log.error("Sensor read error key={}", k, e);
                        }
                    }, 0, 2, TimeUnit.SECONDS)
            );
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
                    log.info("⚡ 모든 세션 종료 → 포트 {} 스케줄러 중단", subscriptionKey);
                }
            }
        }
    }
}
