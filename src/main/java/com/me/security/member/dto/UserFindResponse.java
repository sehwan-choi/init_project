package com.me.security.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.me.security.member.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserFindResponse(
        @Schema(description = "사용자 고유번호") Long userId,
        @Schema(description = "사용자 이름") String userName,
        @Schema(description = "사용자 이메일") String email,
        @Schema(description = "생성일") @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss") LocalDateTime createdAt) {

    public static UserFindResponse userToResponse(User user) {
        return UserFindResponse.builder()
                .userId(user.getId())
                .userName(user.getName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
