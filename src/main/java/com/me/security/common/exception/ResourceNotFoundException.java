package com.me.security.common.exception;

public class ResourceNotFoundException extends SourceRootException{

    public ResourceNotFoundException() {
        super(ExceptionCommonCode.RESOURCE_NOT_FOUND_ERROR);
    }
    public ResourceNotFoundException(String code, Object... args) {
        super(code, args);
    }

    public ResourceNotFoundException(Throwable cause, String code, Object... args) {
        super(cause, code, args);
    }
}
