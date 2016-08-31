package com.cdesign.spittr.config;

import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Created by RealXaker on 31.08.2016.
 */
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //registry.addHandler(myHandler(), "/my");
    }
//
//    @Bean
//    public MyHandler myHandler() {
//        return new MyHandler();
//    }
}
