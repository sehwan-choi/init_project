package com.me.security.common.exception;

public class ResourceNotFoundException extends SourceRootException{

    public ResourceNotFoundException() {
        super(ExceptionCommonCode.RESOURCE_NOT_FOUND_ERROR);
    }
    public ResourceNotFoundException(String code, String... args) {
        super(code, args);
    }

    public ResourceNotFoundException(Throwable cause, String code, String... args) {
        super(cause, code, args);
    }
}
