package com.me.security.common.generator.strategy;


import java.util.UUID;

/**
 * UUID 기반 하이픈('-')을 포함하지 않은 정책
 * 하이픈(
 */
public class NoHyphenGenerateStrategy extends AbstractGenerateStrategy {

    private static final int NO_HYPHEN_DEFAULT_MAX_KEY_SIZE_POLICY = DEFAULT_MAX_KEY_SIZE_POLICY - UUID_HYPHEN_SIZE;

    public NoHyphenGenerateStrategy() {
        super(NO_HYPHEN_DEFAULT_MAX_KEY_SIZE_POLICY, DEFAULT_MIN_KEY_SIZE_POLICY);
        super.keySizePolicy = NO_HYPHEN_DEFAULT_MAX_KEY_SIZE_POLICY;
    }

    @Override
    protected String createUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
