package com.topazkang.homehubagent.monitor;

import com.topazkang.homehubagent.event.PlayerActiveEvent;
import com.topazkang.homehubagent.event.PlayerEmptyEvent;
import com.topazkang.homehubagent.event.RuntimeEventListener;
import com.topazkang.homehubagent.runtime.RuntimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonitorService {

    private final RuntimeService runtimeService;
    private final MonitorClient monitorClient;
    private final ApplicationEventPublisher eventPublisher;

    private volatile NodeStatus currentStatus = NodeStatus.OFFLINE;
    private volatile NodeMetrics currentMetrics;

    private Integer previousPlayerCount = null;

    public void polling() {
        currentStatus = runtimeService.checkAlive();

        if (currentStatus == NodeStatus.OFFLINE) {
            previousPlayerCount = null;
            currentMetrics = null;
            return;
        }

        NodeMetrics metrics = monitorClient.getMetrics();
        currentMetrics = metrics;

        int current = metrics.playerCount();

        if (previousPlayerCount == null) {
            previousPlayerCount = current;

            if (current == 0) {
                eventPublisher.publishEvent(new PlayerEmptyEvent());
            }

            return;
        }

        if (previousPlayerCount > 0 && current == 0) {
            eventPublisher.publishEvent(new PlayerEmptyEvent());
        }

        if (previousPlayerCount == 0 && current > 0) {
            eventPublisher.publishEvent(new PlayerActiveEvent());
        }

        previousPlayerCount = current;
    }

    public NodeStatus getStatus() {
        return currentStatus;
    }

    public NodeMetrics getMetrics() {
        return currentMetrics;
    }
}