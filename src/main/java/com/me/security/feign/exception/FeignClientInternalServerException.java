package com.me.security.feign.exception;

import com.me.security.common.dto.ErrorResponse;

public class FeignClientInternalServerException extends FeignClientBaseException{

    public FeignClientInternalServerException(ErrorResponse errorResponse) {
        super(errorResponse);
    }
}
