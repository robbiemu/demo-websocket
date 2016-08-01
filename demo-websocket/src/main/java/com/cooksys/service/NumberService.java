package com.cooksys.service;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cooksys.component.NumberHandler;

@Service
public class NumberService {

	// concurrency locked counter
    private AtomicInteger counter = new AtomicInteger(0);

    @Autowired
    NumberHandler numberHandler;

    public void send() {
    	System.out.println("Sending number: " + counter.get());
    	// after the counter is incremented, call this callback.
        numberHandler.counterIncrementedCallback(counter.get());
    }
    
    public void increment()
    {
    	System.out.println("New value: " + counter.incrementAndGet());
    	this.send();
    }

}