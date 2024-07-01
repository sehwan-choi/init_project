package com.me.security.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class ErrorResponse {

    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss.SSS")
    private LocalDateTime timestamp;

    private String path;

    public ErrorResponse(String path) {
        this.timestamp = LocalDateTime.now();
        this.path = path;
    }
}
