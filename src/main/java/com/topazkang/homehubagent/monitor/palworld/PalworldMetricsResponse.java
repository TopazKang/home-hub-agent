package com.topazkang.homehubagent.monitor.palworld;

public record PalworldMetricsResponse(int currentplayernum,
                                      int serverfps,
                                      double serverfpsaverage,
                                      double serverframetime,
                                      int days,
                                      int maxplayernum,
                                      int basecampnum,
                                      long uptime) {
}
