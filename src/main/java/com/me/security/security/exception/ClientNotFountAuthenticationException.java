package com.me.security.security.exception;

import org.springframework.security.core.AuthenticationException;

public class ClientNotFountAuthenticationException extends AuthenticationException {
    public ClientNotFountAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ClientNotFountAuthenticationException(String msg) {
        super(msg);
    }
}
