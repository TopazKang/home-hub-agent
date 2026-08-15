package com.topazkang.homehubagent.monitor;

public record NodeMetrics(int playerCount,
                          int serverFps,
                          double averageFps,
                          long uptime) {

}
