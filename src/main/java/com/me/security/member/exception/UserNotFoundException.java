package com.me.security.member.exception;

import com.me.security.common.exception.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {

    private final String message;

    public UserNotFoundException(Long userId) {
        this.message = "User not Found! userId : " + userId;
    }

    public UserNotFoundException(String email) {
        this.message = "User not Found! email : " + email;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
