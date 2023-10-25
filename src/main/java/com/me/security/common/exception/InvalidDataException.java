package com.me.security.common.exception;

public class InvalidDataException extends SourceRootException{

    public InvalidDataException() {
        super(ExceptionCommonCode.BAD_REQUEST_ERROR);
    }

    public InvalidDataException(String code, String... args) {
        super(code, args);
    }

    public InvalidDataException(Throwable cause, String code, String... args) {
        super(cause, code, args);
    }
}
