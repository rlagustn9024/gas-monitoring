package com.elim.server.gas_monitoring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 클라이언트가 구독할 prefix
        registry.enableSimpleBroker("/topic");
        // 서버로 메시지 보낼 때 prefix
        registry.setApplicationDestinationPrefixes("/app");
    }


    /**
     * 여기에 있는 엔드포인트(ws-sensor/websocket)으로 요청 보내면 웹소켓으로 연결됨(구독은 아직 안한 상태)
     * */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 클라이언트 연결 endpoint
        registry.addEndpoint("/ws/sensor")
                .setAllowedOriginPatterns("*");
    }
}
