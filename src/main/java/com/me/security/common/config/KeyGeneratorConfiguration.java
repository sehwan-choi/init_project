package com.me.security.common.config;

import com.me.security.common.generator.KeyGenerator;
import com.me.security.common.generator.UUIDKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeyGeneratorConfiguration {

    private static final int LOG_KEY_DEFAULT_SIZE = 10;

    @Bean
    public KeyGenerator logKeyGenerator() {
        UUIDKeyGenerator generator = new UUIDKeyGenerator();
        generator.setHyphen(false);
        generator.setKeySizePolicy(LOG_KEY_DEFAULT_SIZE);
        return generator;
    }

    @Bean
    public KeyGenerator externalKeyGenerator() {
        UUIDKeyGenerator generator = new UUIDKeyGenerator();
        generator.setUpper(true);
        return generator;
    }
}
