package com.me.security.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@ToString
public class ErrorResponse {

    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss.SSS")
    private LocalDateTime timestamp;

    private String message;

    private String path;

    private Long code;

    public ErrorResponse(LocalDateTime timestamp, String message, String path) {
        this.timestamp = timestamp;
        this.message = message;
        this.path = path;
    }
}
