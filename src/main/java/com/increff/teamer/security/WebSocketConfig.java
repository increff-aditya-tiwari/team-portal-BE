package com.increff.teamer.security;

import com.increff.teamer.util.WebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
//@EnableWebSocketMessageBroker
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(),"/send-notification/{token}")
                .setAllowedOrigins("*");
    }
    @Bean
    public org.springframework.web.socket.WebSocketHandler webSocketHandler() {
        return new WebSocketHandler();
    }

}