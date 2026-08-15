package com.topazkang.homehubagent.node;

import com.topazkang.homehubagent.monitor.MonitorService;
import com.topazkang.homehubagent.monitor.NodeMetrics;
import com.topazkang.homehubagent.monitor.NodeStatus;
import com.topazkang.homehubagent.node.dto.NodeJoinRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/node")
@RequiredArgsConstructor
public class NodeController {

    private final NodeService nodeService;
    private final MonitorService monitorService;

    @PostMapping("/join")
    public void join(@RequestBody NodeJoinRequest request) {
        nodeService.joinNode(request);
    }

    @GetMapping("/info")
    public ResponseEntity<NodeMetrics> getInfo() {
        NodeMetrics metrics = monitorService.getMetrics();

        if (metrics == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(metrics);
    }
}
