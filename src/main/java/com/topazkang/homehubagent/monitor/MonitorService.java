package com.topazkang.homehubagent.monitor;

import com.topazkang.homehubagent.runtime.RuntimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonitorService {

    private final RuntimeService runtimeService;

    private volatile NodeStatus currentStatus = NodeStatus.OFFLINE;

    public void polling() {
        currentStatus = runtimeService.checkAlive();
        System.out.println(currentStatus);
    }

    public NodeStatus getStatus() {
        return currentStatus;
    }
}
