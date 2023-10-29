package com.me.security.member.exception;

import com.me.security.common.exception.InvalidDataException;

public class UserEmailDuplicateException extends InvalidDataException {

    private final String message;

    public UserEmailDuplicateException(Long userId, String email) {
        super("duplicate.user.name");
        this.message = "User Email duplicate! userId : " + userId + " email : " + email;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
