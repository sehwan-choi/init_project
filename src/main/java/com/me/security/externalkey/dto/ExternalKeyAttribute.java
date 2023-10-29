package com.me.security.externalkey.dto;

import com.me.security.externalkey.domain.ExternalKey;

import java.time.LocalDateTime;

public record ExternalKeyAttribute(
        Long id,
        String apiKey,
        String name,
        boolean block,
        LocalDateTime startDate,
        LocalDateTime endDate) {

    public ExternalKeyAttribute(ExternalKey key) {
        this(key.getId(), key.getApiKey(), key.getName(), key.isBlock(), key.getStartDate(), key.getEndDate());
    }
}
