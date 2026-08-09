package com.topazkang.homehubagent.node;

import com.topazkang.homehubagent.node.dto.NodeJoinRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/node")
@RequiredArgsConstructor
public class NodeController {

    private final NodeService nodeService;

    @PostMapping("/join")
    public void join(@RequestBody NodeJoinRequest request) {
        nodeService.joinNode(request);
    }
}
