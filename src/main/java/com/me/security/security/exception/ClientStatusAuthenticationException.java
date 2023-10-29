package com.me.security.security.exception;

import org.springframework.security.core.AuthenticationException;

public class ClientStatusAuthenticationException extends AuthenticationException {
    public ClientStatusAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ClientStatusAuthenticationException(String msg) {
        super(msg);
    }
}
