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
                    "sudo",
                    "-n",
                    "/usr/local/bin/homehub-firewall",
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
                ip,
                gamePort,
                protocol
        );
    }

    public void closeIp(String ip) {
        executeUfw(
                "delete",
                ip,
                gamePort,
                protocol
        );
    }

    private void executeUfw(String... args) {
        try {
            String[] command = new String[args.length + 3];

            command[0] = "sudo";
            command[1] = "-n";
            command[2] = "/usr/local/bin/homehub-firewall";

            System.arraycopy(args, 0, command, 3, args.length);

            Process process = new ProcessBuilder(command).start();

            String output = new String(
                    process.getInputStream().readAllBytes()
            );

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