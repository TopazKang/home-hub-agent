package com.topazkang.homehubagent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HomeHubAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeHubAgentApplication.class, args);
    }

}
