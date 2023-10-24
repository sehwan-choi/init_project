package com.me.security.member.exception;

public class UserNotFoundException extends RuntimeException{

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
