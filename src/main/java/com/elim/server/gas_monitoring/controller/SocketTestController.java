package com.elim.server.gas_monitoring.controller;

import com.elim.server.gas_monitoring.dto.response.ua58kfg.UA58KFGMeasurementResponseDto;
import com.elim.server.gas_monitoring.service.SensorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SocketTestController {

    private final SimpMessagingTemplate messagingTemplate;
    private final SensorService sensorService;
    private final SimpUserRegistry userRegistry;


    // 특정 포트를 구독한 클라이언트에게 push
    @Scheduled(fixedRate = 2000)
    public void publishSensorData() {
        // 현재 등록된 모든 구독을 확인
        userRegistry.getUsers().forEach(user ->
                user.getSessions().forEach(session ->
                        session.getSubscriptions().forEach(subscription -> {
                            String destination = subscription.getDestination();
                            if (destination.startsWith("/topic/sensor/")) {
                                // 구독한 포트명 추출
                                String port = destination.substring("/topic/sensor/".length());
                                UA58KFGMeasurementResponseDto dto = sensorService.readValuesFromKFG(port);
                                messagingTemplate.convertAndSend(destination, dto);
                                System.out.println("📡 Sent sensor data to subscribers of " + port);
                            }
                        })
                )
        );
    }
}
