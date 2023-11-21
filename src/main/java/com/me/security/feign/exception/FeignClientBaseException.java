package com.me.security.feign.exception;

import com.me.security.common.dto.ErrorResponse;

public abstract class FeignClientBaseException extends RuntimeException{

    private final ErrorResponse errorResponse;

    public FeignClientBaseException(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    @Override
    public String getMessage() {
        return errorResponse.toString();
    }
}
