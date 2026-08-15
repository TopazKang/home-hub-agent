package com.topazkang.homehubagent.monitor;

import com.topazkang.homehubagent.event.PlayerActiveEvent;
import com.topazkang.homehubagent.event.PlayerEmptyEvent;
import com.topazkang.homehubagent.event.RuntimeEventListener;
import com.topazkang.homehubagent.runtime.RuntimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonitorService {

    private final RuntimeService runtimeService;
    private final MonitorClient monitorClient;
    private final RuntimeEventListener runtimeEventListener;

    private volatile NodeStatus currentStatus = NodeStatus.OFFLINE;

    private Integer previousPlayerCount = null;

    public void polling() {
        currentStatus = runtimeService.checkAlive();

        NodeMetrics metrics = monitorClient.getMetrics();
        int current = metrics.playerCount();

        // 최초 조회
        if (previousPlayerCount == null) {
            previousPlayerCount = current;

            if (current == 0) {
                runtimeEventListener.onPlayerEmpty(new PlayerEmptyEvent());
            }

            return;
        }

        // N → 0
        if (previousPlayerCount > 0 && current == 0) {
            runtimeEventListener.onPlayerEmpty(new PlayerEmptyEvent());
        }

        // 0 → N
        if (previousPlayerCount == 0 && current > 0) {
            runtimeEventListener.onPlayerActive(new PlayerActiveEvent());
        }

        previousPlayerCount = current;
    }

    public NodeStatus getStatus() {
        return currentStatus;
    }

    public NodeMetrics getMetrics() {
        return monitorClient.getMetrics();
    }
}
