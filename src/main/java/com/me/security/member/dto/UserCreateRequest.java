package com.me.security.member.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record UserCreateRequest(
        @Schema(description = "사용자 이름") @NotNull String userName,
        @Schema(description = "로그인 패스워드") @NotNull String password,
        @Schema(description = "로그인 이메일") @NotNull String email) {

}
