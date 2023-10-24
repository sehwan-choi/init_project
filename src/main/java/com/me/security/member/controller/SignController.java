package com.me.security.member.controller;

import com.me.security.member.dto.LoginRequest;
import com.me.security.member.dto.LoginSuccessResponse;
import com.me.security.member.dto.UserCreateRequest;
import com.me.security.member.dto.UserCreateResponse;
import com.me.security.member.service.SignService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sign")
@RequiredArgsConstructor
public class SignController {

    private final SignService signService;

    @PostMapping("/signin")
    public LoginSuccessResponse login(@Valid @RequestBody LoginRequest request) {
        return signService.login(request);
    }

    @PostMapping("/signup")
    public UserCreateResponse signup(@Valid @RequestBody UserCreateRequest request) {
        return signService.signup(request);
    }
}
