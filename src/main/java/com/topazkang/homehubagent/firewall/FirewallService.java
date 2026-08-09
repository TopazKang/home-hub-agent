package com.topazkang.homehubagent.firewall;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FirewallService {

    private final String gamePort;
    private final String protocol;

    public FirewallService(
            @Value("${node.game-port}") String gamePort,
            @Value("${node.protocol}") String protocol
    ) {
        this.gamePort = gamePort;
        this.protocol = protocol;
    }

    public boolean checkIpOpen(String ip) {
        try {
            Process process = new ProcessBuilder(
                    "ufw",
                    "status"
            ).start();

            String output = new String(
                    process.getInputStream().readAllBytes()
            );

            int exitCode = process.waitFor();

            if (exitCode != 0) {
                throw new RuntimeException("UFW 상태 조회 실패");
            }

            String target = gamePort + "/" + protocol;

            return output.lines().anyMatch(line ->
                    line.contains(target)
                            && line.contains(ip)
                            && line.contains("ALLOW")
            );

        } catch (Exception e) {
            throw new RuntimeException("방화벽 조회 실패", e);
        }
    }

    public void openIp(String ip) {
        executeUfw(
                "allow",
                "from", ip,
                "to", "any",
                "port", gamePort,
                "proto", protocol
        );
    }

    public void closeIp(String ip) {
        executeUfw(
                "delete",
                "allow",
                "from", ip,
                "to", "any",
                "port", gamePort,
                "proto", protocol
        );
    }

    private void executeUfw(String... args) {
        try {
            String[] command = new String[args.length + 1];
            command[0] = "ufw";

            System.arraycopy(
                    args,
                    0,
                    command,
                    1,
                    args.length
            );

            Process process = new ProcessBuilder(command).start();

            String error = new String(
                    process.getErrorStream().readAllBytes()
            );

            int exitCode = process.waitFor();

            if (exitCode != 0) {
                throw new RuntimeException(
                        "UFW 명령 실패: " + error
                );
            }

        } catch (Exception e) {
            throw new RuntimeException(
                    "방화벽 명령 실행 실패",
                    e
            );
        }
    }
}