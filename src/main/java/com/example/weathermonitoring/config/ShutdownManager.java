package com.example.weathermonitoring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class ShutdownManager implements ApplicationListener<ContextClosedEvent> {

    private final ExecutorService executorService;

    @Autowired
    public ShutdownManager(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        shutdownExecutor();
    }

    private void shutdownExecutor() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}
