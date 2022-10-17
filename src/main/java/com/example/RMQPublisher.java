package com.example;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.smallrye.mutiny.Multi;

@ApplicationScoped
public class RMQPublisher {

    @Outgoing("to-rabbitmq")
    public Multi<Price> prices() { 
        AtomicInteger count = new AtomicInteger();
        return Multi.createFrom()
                .ticks()
                .every(Duration.ofMillis(1000))
                .map(l -> new Price().setPrice(count.incrementAndGet()))
                .onOverflow()
                .drop();
    }
}