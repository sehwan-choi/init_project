package com.me.security.member.controller;

import com.me.security.common.code.ServerCode;
import com.me.security.common.model.ApiResultResponse;
import com.me.security.common.swagger.annotation.SwaggerApiExampleValues;
import com.me.security.member.dto.LoginRequest;
import com.me.security.member.dto.LoginResult;
import com.me.security.member.dto.UserCreateRequest;
import com.me.security.member.service.SignService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sign")
@RequiredArgsConstructor
@Tag(name = "회원가입 / 로그인 관련 API", description = "회원가입 / 로그인 관련 API")
public class SignController {

    private final SignService signService;

    @PostMapping("/login")
    @SwaggerApiExampleValues({
            ServerCode.SUCCESS,
            ServerCode.BAD_REQUEST,
            ServerCode.LOGIN_FAIL
    })
    @Operation(summary = "Login API", description = "Login API")
    public ApiResultResponse<LoginResult> login(@Valid @RequestBody LoginRequest request) {
        return ApiResultResponse.success(signService.login(request));
    }

    @PostMapping("/signup")
    @SwaggerApiExampleValues({
            ServerCode.SUCCESS,
            ServerCode.BAD_REQUEST,
            ServerCode.SIGNUP_DUPLICATE_EMAIL
    })
    @Operation(summary = "회원 가입 API", description = "회원 가입 API")
    public ApiResultResponse signup(@Valid @RequestBody UserCreateRequest request) {
        signService.signup(request);
        return ApiResultResponse.success();
    }
}
