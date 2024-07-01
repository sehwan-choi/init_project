package com.me.security.common.exception;

import com.me.security.common.code.ServerCode;

public class ResourceNotFoundException extends SourceRootException{

    public ResourceNotFoundException() {
        super(ServerCode.NOT_FOUND);
    }
    public ResourceNotFoundException(ServerCode code) {
        super(code);
    }

    protected ResourceNotFoundException(ServerCode code, String message) {
        super(code, message);
    }
}
