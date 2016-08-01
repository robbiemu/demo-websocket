package com.cooksys.component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.cooksys.service.MessageService;
import com.cooksys.service.NumberService;
import com.cooksys.tx.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
public class MessageHandler extends TextWebSocketHandler {
	@Autowired
	MessageService messageService;
	
	Gson gson = new GsonBuilder().create(); 

	HashSet<WebSocketSession> sessions = new HashSet<WebSocketSession>();

	public void messageCallback(Message msg) {
		Iterator<WebSocketSession> iterator = sessions.iterator();
		while (iterator.hasNext()) {
			WebSocketSession session = iterator.next();
			if (session != null && session.isOpen()) {		
				try {
					session.sendMessage(new TextMessage("{\"username\": \"" + msg.getUsername() + "\", \"message\": \"" + msg.getMessage() + "\"}"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("Session is not open. Removing session");
				iterator.remove();
			}
		}
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) {	
		sessions.add(session);
		System.out.println("New connection established. Current connections: " + sessions.size());
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		//System.out.println(message.getPayload());
		if ("CLOSE".equalsIgnoreCase(message.getPayload())) {
			session.close();
			sessions.remove(session);
			System.out.println("Session closed. Current connections: " + sessions.size());
		} else if (message.getPayload().startsWith("MESSAGE")) {
			String[] jsm = message.getPayload().split(" ", 2);
			Message m = gson.fromJson(jsm[1], Message.class);
			System.out.println("received MESSAGE: " + m);
			messageService.message(m);
		}
	}

}
