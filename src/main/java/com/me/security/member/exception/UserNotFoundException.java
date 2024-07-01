package com.me.security.member.exception;

import com.me.security.common.code.ServerCode;
import com.me.security.common.exception.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {


    public UserNotFoundException(Long userId) {
        super(ServerCode.BAD_REQUEST, "User not Found! userId : " + userId);
    }

    public UserNotFoundException(String email) {
        super(ServerCode.BAD_REQUEST, "User not Found! email : " + email);
    }
}
