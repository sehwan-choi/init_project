package com.me.security.externalkey.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.me.security.common.serializer.RequestLocalDateTimeDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record KeyRegistrationRequest(
        @Schema(description = "API Key 를 발급할 서비스 이름") @NotNull String name,
        @Schema(description = "API Key 사용 시작 시간(YYYY-MM-DD HH:mm:ss)", example = "2024-07-02 12:00:00") @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
        @Schema(description = "API Key 사용 만료 기간(YYYY-MM-DD HH:mm:ss)", example = "2024-12-31 23:59:59") @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {

    public KeyRegistrationRequest {
        if (startTime == null || endTime == null) {
            startTime = LocalDateTime.now();
            endTime = LocalDateTime.now().plusDays(1);
        }
    }
}
