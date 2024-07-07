package com.me.security.common.generator.strategy;

import java.util.UUID;


/**
 * UUID 기반 키 생성 정책
 * 하이픈('-')을 포함한 총 길이36를 기본 정책으로 한다.
 */
public class DefaultGenerateStrategy extends AbstractGenerateStrategy{

    public DefaultGenerateStrategy() {
        super(DEFAULT_MAX_KEY_SIZE_POLICY, DEFAULT_MIN_KEY_SIZE_POLICY);
        super.keySizePolicy = DEFAULT_MAX_KEY_SIZE_POLICY;
    }

    @Override
    protected String createUUID() {
        return UUID.randomUUID().toString();
    }
}
