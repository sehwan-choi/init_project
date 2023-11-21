package com.me.security.feign.exception;

import com.me.security.common.dto.ErrorResponse;

public class FeignClientNotFoundException extends FeignClientBaseException{

    public FeignClientNotFoundException(ErrorResponse errorResponse) {
        super(errorResponse);
    }
}
