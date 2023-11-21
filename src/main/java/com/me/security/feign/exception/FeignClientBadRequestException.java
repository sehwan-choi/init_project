package com.me.security.feign.exception;

import com.me.security.common.dto.ErrorResponse;

public class FeignClientBadRequestException extends FeignClientBaseException{

    public FeignClientBadRequestException(ErrorResponse errorResponse) {
        super(errorResponse);
    }
}
