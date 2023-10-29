package com.me.security.externalkey.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.me.security.common.serializer.RequestLocalDateTimeDeserializer;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record KeyRegistrationRequest(
        @NotNull String name,
        @JsonDeserialize(using = RequestLocalDateTimeDeserializer.class) LocalDateTime startTime,
        @JsonDeserialize(using = RequestLocalDateTimeDeserializer.class) LocalDateTime endTime) {

    public KeyRegistrationRequest {
        if (startTime == null || endTime == null) {
            startTime = LocalDateTime.now();
            endTime = LocalDateTime.now().plusDays(1);
        }
    }
}
