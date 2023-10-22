package com.me.security.member.controller;

import com.me.security.member.dto.LoginRequest;
import com.me.security.member.dto.LoginSuccessResponse;
import com.me.security.member.service.SignService;
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

    @PostMapping("/login")
    public LoginSuccessResponse login(@RequestBody LoginRequest request) {
        return signService.login(request);
    }
}
