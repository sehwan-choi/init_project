package com.me.security.member.exception;

import com.me.security.common.code.ServerCode;
import com.me.security.common.exception.InvalidDataException;

public class LoginException extends InvalidDataException {

    public LoginException(String message) {
        super(ServerCode.LOGIN_FAIL, message);
    }
}
