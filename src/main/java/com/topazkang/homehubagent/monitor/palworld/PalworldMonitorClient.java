package com.topazkang.homehubagent.monitor.palworld;
import com.topazkang.homehubagent.monitor.MonitorClient;
import com.topazkang.homehubagent.monitor.NodeMetrics;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PalworldMonitorClient implements MonitorClient {

    private final PalworldRestClient palworldRestClient;

    @Override
    public NodeMetrics getMetrics() {
        PalworldMetricsResponse response = palworldRestClient.getMetrics();

        return new NodeMetrics(response.currentplayernum(),
                                response.serverfps(),
                                response.serverfpsaverage(),
                                response.uptime());
    }
    //TODO: 추후에 접속자 정보 필요해지면 사용 (response 파싱해서 for 문으로 NodePlayer에 담고 List로 묶어서 NodePlayers로 반환)
//    public NodePlayers getPlayers() {
//        PalworldPlayersResponse response = palworldRestClient.getPlayers();
//
//        return new NodePlayers()
//    }
}
