package com.me.security.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SimpleResponse {

    private boolean success;

    private String message;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss.SSS")
    private LocalDateTime responseTime;

    public static SimpleResponse ok() {
        SimpleResponse simpleResponse = new SimpleResponse();
        simpleResponse.success = true;
        simpleResponse.message = null;
        simpleResponse.responseTime = LocalDateTime.now();
        return simpleResponse;
    }
}
