package com.me.security.externalkey.dto;

import com.me.security.externalkey.domain.ExternalKey;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record ExternalKeyCheckResponse(
        @Schema(description = "API Key 고유번호") Long id,
        @Schema(description = "API Key 이용 서비스 이름") String name,
        @Schema(description = "Block 상태") boolean block,
        @Schema(description = "API Key 사용 시작 시간") LocalDateTime startDate,
        @Schema(description = "API Key 사용 만료 기간") LocalDateTime endDate) {

    public ExternalKeyCheckResponse(ExternalKey key) {
        this(key.getId(), key.getName(), key.isBlock(), key.getStartDate(), key.getEndDate());
    }
}
