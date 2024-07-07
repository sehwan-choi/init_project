package com.me.security.common.config;

import com.me.security.common.generator.KeyGenerator;
import com.me.security.common.generator.UUIDKeyGenerator;
import com.me.security.common.generator.strategy.DefaultGenerateStrategy;
import com.me.security.common.generator.strategy.NoHyphenGenerateStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeyGeneratorConfiguration {

    private static final int LOG_KEY_DEFAULT_SIZE = 10;

    @Bean
    public KeyGenerator logKeyGenerator() {
        NoHyphenGenerateStrategy noHyphenGenerateStrategy = new NoHyphenGenerateStrategy();
        noHyphenGenerateStrategy.setKeySize(LOG_KEY_DEFAULT_SIZE);
        UUIDKeyGenerator generator = new UUIDKeyGenerator(noHyphenGenerateStrategy);
        return generator;
    }

    @Bean
    public KeyGenerator externalKeyGenerator() {
        UUIDKeyGenerator generator = new UUIDKeyGenerator(new DefaultGenerateStrategy());
        generator.setUpper(true);
        return generator;
    }
}
