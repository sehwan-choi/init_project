package com.me.security.common.exception;

import com.me.security.common.code.ServerCode;
import lombok.Getter;

public abstract class SourceRootException extends RuntimeException{

    @Getter
    private final ServerCode code;

    protected SourceRootException(ServerCode code) {
        super(code.getMessageCode());
        this.code = code;
    }

    protected SourceRootException(ServerCode code, Throwable cause) {
        super(code.getCode(), cause);
        this.code = code;
    }

    protected SourceRootException(ServerCode code, String message) {
        super(message);
        this.code = code;
    }

    protected SourceRootException(ServerCode code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
