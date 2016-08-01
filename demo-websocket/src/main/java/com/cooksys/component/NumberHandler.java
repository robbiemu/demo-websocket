package com.cooksys.component;

import java.util.HashSet;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.cooksys.service.NumberService;

//%instructable% Step 3: create websocket handlers - these can be generic components
@Component
//%instructable% Step 3 - continued: since we are sending text messages, there's a handy parent class we can use:
public class NumberHandler extends TextWebSocketHandler {
	@Autowired
	NumberService numberService;
	
	HashSet<WebSocketSession> sessions = new HashSet<WebSocketSession>();

	public void counterIncrementedCallback(int counter) {
		Iterator<WebSocketSession> iterator = sessions.iterator();
		while (iterator.hasNext()) {
			
			WebSocketSession session = iterator.next();
			if (session != null && session.isOpen()) {		
				try {
					session.sendMessage(new TextMessage("{\"number\": \"" + counter + "\"}"));
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
		if ("CLOSE".equalsIgnoreCase(message.getPayload())) {
			session.close();
			sessions.remove(session);
			System.out.println("Session closed. Current connections: " + sessions.size());
		} else if ("INCREMENT".equalsIgnoreCase(message.getPayload())) {
			numberService.increment();
		}
	}
}
