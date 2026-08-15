package com.topazkang.homehubagent.node;

import com.topazkang.homehubagent.monitor.NodeMetrics;
import com.topazkang.homehubagent.node.dto.NodeJoinRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/node")
@RequiredArgsConstructor
public class NodeController {

    private final NodeService nodeService;

    @PostMapping("/join")
    public void join(@RequestBody NodeJoinRequest request) {
        nodeService.joinNode(request);
    }

    @GetMapping("/info")
    public NodeMetrics getInfo(){
        return nodeService.getMetricInfo();
    }
}
