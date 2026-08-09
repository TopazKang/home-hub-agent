package com.topazkang.homehubagent.node;

import com.topazkang.homehubagent.firewall.FirewallService;
import com.topazkang.homehubagent.monitor.MonitorService;
import com.topazkang.homehubagent.node.dto.NodeJoinRequest;
import com.topazkang.homehubagent.runtime.RuntimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

@Service
@RequiredArgsConstructor
public class NodeService {

    private final MonitorService monitorService;
    private final RuntimeService runtimeService;
    private final FirewallService firewallService;

    public void joinNode(NodeJoinRequest request){

        if (!monitorService.checkAlive()){
            runtimeService.startUp();
        }

        if (!firewallService.checkIpOpen(request.ip())){
            firewallService.openIp(request.ip());
        }

        System.out.println(request);
    }
}
