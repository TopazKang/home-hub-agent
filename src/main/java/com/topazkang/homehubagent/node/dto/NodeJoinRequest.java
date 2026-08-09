package com.topazkang.homehubagent.node.dto;

public record NodeJoinRequest(
        String userId,
        String userName,
        String ip
) {
}
