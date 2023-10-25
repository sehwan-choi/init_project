package com.me.security.common.exception;

import lombok.Getter;

public class SourceRootException extends RuntimeException{

    @Getter
    private final String code;

    @Getter
    private final Object[] args;

    public SourceRootException(String code, String... args) {
        this.code = code;
        this.args = args;
    }

    public SourceRootException(Throwable cause, String code, String... args) {
        super(cause);
        this.code = code;
        this.args = args;
    }
}
