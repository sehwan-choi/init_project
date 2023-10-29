package com.me.security.externalkey.dto;

import com.me.security.externalkey.domain.ExternalKey;

import java.time.LocalDateTime;

public record ExternalKeyCheckResponse(
        Long id,
        String name,
        boolean block,
        LocalDateTime startDate,
        LocalDateTime endDate) {

    public ExternalKeyCheckResponse(ExternalKey key) {
        this(key.getId(), key.getName(), key.isBlock(), key.getStartDate(), key.getEndDate());
    }
}
