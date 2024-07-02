package com.me.security.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record UserCreateResponse(@Schema(description = "") String email,
                                 @Schema(description = "") String userName,
                                 @Schema(description = "") LocalDateTime createdAt) {
}
