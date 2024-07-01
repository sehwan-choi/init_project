package com.me.security.common.exception;

import com.me.security.common.code.ServerCode;

public class InternalServerException extends SourceRootException{

    public InternalServerException() {
        super(ServerCode.INTERNAL_SERVER_ERROR);
    }
    public InternalServerException(ServerCode code) {
        super(code);
    }

    public InternalServerException(ServerCode code, String message) {
        super(code, message);
    }

    public InternalServerException(ServerCode code, Throwable cause) {
        super(code, cause);
    }

    protected InternalServerException(ServerCode code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
