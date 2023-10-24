package com.me.security.security.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class BadTokenAuthorizationException extends BadCredentialsException {

    public BadTokenAuthorizationException(String message) {
        super(message);
    }

    public BadTokenAuthorizationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
