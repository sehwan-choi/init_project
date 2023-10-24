package com.me.security.member.service;

import com.me.security.member.domain.User;
import com.me.security.member.dto.LoginRequest;
import com.me.security.member.dto.LoginSuccessResponse;
import com.me.security.member.dto.UserCreateRequest;
import com.me.security.member.dto.UserCreateResponse;

public interface SignService {

    LoginSuccessResponse login(LoginRequest request);

    UserCreateResponse signup(UserCreateRequest request);
}
