package com.me.security.common.exception;

import lombok.Getter;

public class SourceRootException extends RuntimeException{

    @Getter
    private final String code;

    @Getter
    private final Object[] args;

    public SourceRootException(String code, Object... args) {
        this.code = code;
        this.args = args;
    }

    public SourceRootException(Throwable cause, String code, Object... args) {
        super(cause);
        this.code = code;
        this.args = args;
    }
}
