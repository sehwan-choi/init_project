package com.me.security.common.crypto.exception;

import com.me.security.common.code.ServerCode;
import com.me.security.common.exception.InternalServerException;

public class CryptoException extends InternalServerException {

    public CryptoException(String message, Throwable cause) {
        super(ServerCode.INTERNAL_SERVER_ERROR, message, cause);
    }
}
