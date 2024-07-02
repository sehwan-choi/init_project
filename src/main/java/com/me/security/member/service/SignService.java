package com.me.security.member.service;

import com.me.security.member.dto.LoginRequest;
import com.me.security.member.dto.LoginResult;
import com.me.security.member.dto.UserCreateRequest;
import com.me.security.member.dto.UserCreateResponse;

public interface SignService {

    LoginResult login(LoginRequest request);

    UserCreateResponse signup(UserCreateRequest request);
}
