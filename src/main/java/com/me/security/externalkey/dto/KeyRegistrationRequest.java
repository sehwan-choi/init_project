package com.me.security.externalkey.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record KeyRegistrationRequest(
        @NotNull String name,
        @DateTimeFormat(pattern = "YYYY-MM-DD HH:mm:dd") LocalDateTime startTime,
        @DateTimeFormat(pattern = "YYYY-MM-DD HH:mm:dd") LocalDateTime endTime) {

    public KeyRegistrationRequest {
        startTime = LocalDateTime.now();
        endTime = LocalDateTime.now().plusDays(1);
    }

    public boolean isDate() {
        return startTime != null && endTime != null;
    }
}
