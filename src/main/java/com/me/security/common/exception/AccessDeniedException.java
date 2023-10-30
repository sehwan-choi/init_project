package com.me.security.common.exception;

public class AccessDeniedException extends SourceRootException {

    public AccessDeniedException() {
        super(ExceptionCommonCode.ACCESS_DENIED_ERROR);
    }

    public AccessDeniedException(String errorCode, Object... args) {
        super(ExceptionCommonCode.ACCESS_DENIED_ERROR, args);
    }
}
