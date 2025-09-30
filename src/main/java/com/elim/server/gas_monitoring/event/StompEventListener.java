package com.elim.server.gas_monitoring.event;

import com.elim.server.gas_monitoring.dto.response.ua58kfg.UA58KFGMeasurementResponseDto;
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

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final SensorService sensorService;
    private final SimpMessagingTemplate messagingTemplate;

    // 세션 ID -> destination 매핑
    private final Map<String, String> sessionSubscriptions = new ConcurrentHashMap<>();

    // 포트별 스케줄러 관리
    private final Map<String, ScheduledFuture<?>> portSchedulers = new ConcurrentHashMap<>();



    @EventListener
    public void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        String destination = accessor.getDestination();

        if (destination != null && destination.startsWith("/topic/sensor/")) {
            String port = destination.substring("/topic/sensor/".length()); // 포트 번호 추출
            sessionSubscriptions.put(sessionId, port); // 해당 세션이 어떤 포트를 구독중인지 기록
            log.info("✅ 구독자 추가 sessionId={}, port={}", sessionId, port);

            // 포트별 스케줄러가 없으면 시작
            portSchedulers.computeIfAbsent(port, p ->
                    scheduler.scheduleAtFixedRate(() -> {
                        try {
                            UA58KFGMeasurementResponseDto dto = sensorService.readValuesFromKFG(p);
                            messagingTemplate.convertAndSend("/topic/sensor/" + p, dto);
                            log.info("📡 Sent sensor data to port={}, data={}", p, dto);
                        } catch (Exception e) {
                            log.error("❌ Sensor read error for port={}", p, e);
                        }
                    }, 0, 2, TimeUnit.SECONDS) // 2초에 한번씩 푸시
            );
        }
    }


    @EventListener
    public void handleSessionUnsubscribeEvent(SessionUnsubscribeEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();

        removeSession(sessionId);
    }


    @EventListener
    public void handleSessionDisconnectEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();

        removeSession(sessionId);
    }

    private void removeSession(String sessionId) {
        String port = sessionSubscriptions.remove(sessionId);
        if (port != null) {
            log.info("❌ 세션 종료 sessionId={}, port={}", sessionId, port);

            // 같은 포트로 구독 중인 세션이 없으면 스케줄러 종료
            boolean stillSubscribed = sessionSubscriptions.containsValue(port);
            if (!stillSubscribed) {
                ScheduledFuture<?> task = portSchedulers.remove(port);
                if (task != null) {
                    task.cancel(false);
                    log.info("⚡ 모든 세션 종료 → 포트 {} 스케줄러 중단", port);
                }
            }
        }
    }
}
