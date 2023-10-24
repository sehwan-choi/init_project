package com.me.security.member.dto;

import java.time.LocalDateTime;

public record UserCreateResponse(String email, String userName, LocalDateTime createdAt) {
}
