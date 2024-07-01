package com.me.security.member.exception;

import com.me.security.common.code.ServerCode;
import com.me.security.common.exception.InvalidDataException;

public class UserEmailDuplicateException extends InvalidDataException {

    public UserEmailDuplicateException(Long userId, String email) {
        super(ServerCode.SIGNUP_DUPLICATE_EMAIL, "User Email duplicate! userId : " + userId + " email : " + email);
    }
}
