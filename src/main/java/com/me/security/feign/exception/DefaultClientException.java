package com.me.security.feign.exception;

import com.me.security.common.code.ServerCode;
import com.me.security.common.exception.SourceRootException;

public class DefaultClientException extends SourceRootException {

    public DefaultClientException(ServerCode code, String message) {
        super(code, message);
    }

    public DefaultClientException(ServerCode code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
