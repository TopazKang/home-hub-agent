package com.topazkang.homehubagent.monitor;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NodeScheduler {

    private final MonitorService monitorService;

    @Scheduled(fixedDelay = 5000)
    public void polling() {
        monitorService.polling();
    }
}
