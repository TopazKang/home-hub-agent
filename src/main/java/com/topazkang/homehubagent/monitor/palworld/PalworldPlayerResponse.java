package com.topazkang.homehubagent.monitor.palworld;

public record PalworldPlayerResponse(String name,
                                     String accountName,
                                     String playerId,
                                     String userId,
                                     String ip,
                                     int ping,
                                     int location_x,
                                     int location_y,
                                     int level,
                                     int building_count) {
}


