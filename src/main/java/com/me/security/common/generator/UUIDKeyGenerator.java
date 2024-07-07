package com.me.security.common.generator;

import com.me.security.common.generator.strategy.UUIDKeyGenerateStrategy;
import lombok.Setter;

/**
 * UUID 기반 Key 를 생성한다.
 */
public class UUIDKeyGenerator implements KeyGenerator{

    @Setter
    private boolean upper = false;

    private final UUIDKeyGenerateStrategy strategy;

    public UUIDKeyGenerator(UUIDKeyGenerateStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public String generate() {
        return upper ? strategy.generateUUID().toUpperCase() : strategy.generateUUID();
    }
}
