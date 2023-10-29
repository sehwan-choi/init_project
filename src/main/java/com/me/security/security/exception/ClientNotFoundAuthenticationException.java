package com.me.security.security.exception;

import org.springframework.security.core.AuthenticationException;

public class ClientNotFoundAuthenticationException extends AuthenticationException {
    public ClientNotFoundAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ClientNotFoundAuthenticationException(String msg) {
        super(msg);
    }
}
