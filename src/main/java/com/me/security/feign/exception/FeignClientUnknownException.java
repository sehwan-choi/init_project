package com.me.security.feign.exception;

import com.me.security.common.dto.ErrorResponse;

public class FeignClientUnknownException extends FeignClientBaseException{

    public FeignClientUnknownException(ErrorResponse errorResponse) {
        super(errorResponse);
    }
}
