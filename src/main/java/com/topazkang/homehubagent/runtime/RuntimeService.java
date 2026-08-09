package com.topazkang.homehubagent.runtime;

import com.topazkang.homehubagent.monitor.NodeStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RuntimeService {

    private final String containerName;

    public RuntimeService(
            @Value("${node.container-name}") String containerName
    ) {
        this.containerName = containerName;
    }

    public NodeStatus checkAlive() {
        try {
            Process process = new ProcessBuilder(
                    "docker",
                    "inspect",
                    "-f",
                    "{{.State.Running}}",
                    containerName
            ).start();

            String result = new String(
                    process.getInputStream().readAllBytes()
            ).trim();

            int exitCode = process.waitFor();

            if (exitCode != 0) {
                return NodeStatus.OFFLINE;
            }

            return Boolean.parseBoolean(result)
                    ? NodeStatus.ONLINE
                    : NodeStatus.OFFLINE;

        } catch (Exception e) {
            throw new RuntimeException(
                    "Docker 컨테이너 상태 조회 실패",
                    e
            );
        }
    }

    public void startUp() {
        executeDockerCommand("start", containerName);
    }

    public void shutDown() {
        executeDockerCommand("stop", containerName);
    }

    public void restart() {
        executeDockerCommand("restart", containerName);
    }

    private void executeDockerCommand(String... args) {
        try {
            String[] command = new String[args.length + 1];
            command[0] = "docker";

            System.arraycopy(
                    args,
                    0,
                    command,
                    1,
                    args.length
            );

            Process process =
                    new ProcessBuilder(command).start();

            int exitCode = process.waitFor();

            if (exitCode != 0) {
                String error = new String(
                        process.getErrorStream().readAllBytes()
                );

                throw new RuntimeException(
                        "Docker 명령 실패: " + error
                );
            }

        } catch (Exception e) {
            throw new RuntimeException(
                    "Docker 명령 실행 실패",
                    e
            );
        }
    }
}