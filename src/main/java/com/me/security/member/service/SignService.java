package com.me.security.member.service;

import com.me.security.member.dto.LoginRequest;
import com.me.security.member.dto.LoginSuccessResponse;

public interface SignService {

    LoginSuccessResponse login(LoginRequest request);
}
