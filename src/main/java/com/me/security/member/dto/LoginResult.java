package com.me.security.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResult(@Schema(description = "AccessToken") String token) {
}
