package com.topazkang.homehubagent.event;

import com.topazkang.homehubagent.runtime.RuntimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ScheduledFuture;

@Component
@RequiredArgsConstructor
public class RuntimeEventListener {

    private final RuntimeService runtimeService;

    private final ThreadPoolTaskScheduler taskScheduler;

    private ScheduledFuture<?> shutdownTask;


    @EventListener
    public synchronized void onPlayerEmpty(PlayerEmptyEvent event) {
        // 혹시 기존 예약이 있으면 제거
        cancelShutdown();

        shutdownTask = taskScheduler.schedule(
                runtimeService::shutDown,
                Instant.now().plus(Duration.ofMinutes(10))
        );
    }

    @EventListener
    public synchronized void onPlayerActive(PlayerActiveEvent event) {
        cancelShutdown();
    }

    private void cancelShutdown() {
        if (shutdownTask != null) {
            shutdownTask.cancel(false);
            shutdownTask = null;
        }
    }
}
