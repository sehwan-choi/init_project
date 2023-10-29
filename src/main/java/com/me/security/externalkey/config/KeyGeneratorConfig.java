package com.me.security.externalkey.config;

import com.me.security.externalkey.service.KeyGenerator;
import com.me.security.externalkey.service.UUIDKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeyGeneratorConfig {

    private final String defaultKeyPrefix = "EXTERNAL-";

    @Bean
    public KeyGenerator UUIDKeyGenerator() {
        UUIDKeyGenerator generator = new UUIDKeyGenerator();
        generator.setKeyPrefix(defaultKeyPrefix);
        return generator;
    }
}
