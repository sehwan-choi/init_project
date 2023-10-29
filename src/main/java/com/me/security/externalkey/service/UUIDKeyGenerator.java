package com.me.security.externalkey.service;

import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDKeyGenerator implements KeyGenerator {

    private static final String PREFIX = "";

    @Setter
    private String keyPrefix = PREFIX;

    @Override
    public String generate() {
        return keyPrefix + UUID.randomUUID().toString();
    }
}
