package com.cooksys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooksys.component.MessageHandler;
import com.cooksys.tx.Message;

@Service
public class MessageService {
    @Autowired
    MessageHandler messageHandler;

    public void message(Message msg) {
    	System.out.println("User " + msg.getUsername() + " sending message: " + msg.getMessage());
        messageHandler.messageCallback(msg);
    }
}
