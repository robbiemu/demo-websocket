package com.cooksys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.cooksys.component.MessageHandler;
import com.cooksys.component.NumberHandler;

@SpringBootApplication
// %instrutable% Step 1: add these websocket specific Annotations:
@EnableWebSocket
@EnableScheduling
// %instrutable% Step 2: configure websocket handlers
public class DemoWebsocketApplication implements WebSocketConfigurer {
	@Autowired
	NumberHandler numberHandler;	
	
	@Autowired
	MessageHandler messageHandler;

	public static void main(String[] args) {
		SpringApplication.run(DemoWebsocketApplication.class, args);
	}
	
	// %instrutable% Step 2 - continued: configure websocket handlers
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry handlerRegistry) {
		handlerRegistry.addHandler(numberHandler, "/number").setAllowedOrigins("*");
		handlerRegistry.addHandler(messageHandler, "/message").setAllowedOrigins("*");
	}
}
