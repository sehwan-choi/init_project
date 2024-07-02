package com.me.security.externalkey.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record KeyRegistrationResponse(@Schema(description = "생성날짜") LocalDateTime createdAt,
                                      @Schema(description = "API Key") String apiKey) {
}
