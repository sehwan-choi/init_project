package com.me.security.common.exception;

public class InternalServerException extends SourceRootException{

    public InternalServerException() {
        super(ExceptionCommonCode.INTERNAL_SERVER_ERROR);
    }
    public InternalServerException(String code, Object... args) {
        super(code, args);
    }

    public InternalServerException(Throwable cause, String code, Object... args) {
        super(cause, code, args);
    }

}
