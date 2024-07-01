package com.me.security.common.exception;

import com.me.security.common.code.ServerCode;

public class AccessDeniedException extends SourceRootException {

    public AccessDeniedException() {
        super(ServerCode.FORBIDDEN);
    }

    public AccessDeniedException(ServerCode errorCode) {
        super(ServerCode.FORBIDDEN);
    }

    protected AccessDeniedException(ServerCode code, String message) {
        super(code, message);
    }
}
