package com.me.security.member.dto;


import jakarta.validation.constraints.NotNull;

public record UserCreateRequest(
        @NotNull String userName,
        @NotNull String password,
        @NotNull String email) {

}
