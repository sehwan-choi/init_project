package com.me.security.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.me.security.member.domain.User;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserFindResponse(
        Long userId,
        String userName,
        String email,
        @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss") LocalDateTime createdAt) {

    public static UserFindResponse userToResponse(User user) {
        return UserFindResponse.builder()
                .userId(user.getId())
                .userName(user.getName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
