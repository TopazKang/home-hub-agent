package com.topazkang.homehubagent.monitor.palworld;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class PalworldRestClient {

    private final RestClient restClient;

    public PalworldRestClient(
            @Value("${palworld.rest-api.url}") String url,
            @Value("${palworld.rest-api.username}") String username,
            @Value("${palworld.rest-api.password}") String password
    ) {
        this.restClient = RestClient.builder()
                .baseUrl(url)
                .defaultHeaders(headers ->
                        headers.setBasicAuth(username, password))
                .build();
    }

    public PalworldMetricsResponse getMetrics() {
        return restClient.get()
                .uri("/v1/api/metrics")
                .retrieve()
                .body(PalworldMetricsResponse.class);
    }

    public PalworldPlayersResponse getPlayers() {
        return restClient.get()
                .uri("/v1/api/players")
                .retrieve()
                .body(PalworldPlayersResponse.class);
    }
}
