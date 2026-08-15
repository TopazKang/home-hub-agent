package com.topazkang.homehubagent.monitor;

import com.topazkang.homehubagent.runtime.RuntimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonitorService {

    private final RuntimeService runtimeService;
    private final MonitorClient monitorClient;

    private volatile NodeStatus currentStatus = NodeStatus.OFFLINE;

    public void polling() {
        currentStatus = runtimeService.checkAlive();

        NodeMetrics nodeMetrics = monitorClient.getMetrics();
        if (nodeMetrics.playerCount() == 0){
            // Event추가
        }
    }

    public NodeStatus getStatus() {
        return currentStatus;
    }

    public NodeMetrics getMetrics() {
        return monitorClient.getMetrics();
    }
}
