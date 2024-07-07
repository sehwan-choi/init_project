package com.me.security.common.generator.strategy;

import java.util.UUID;

public interface UUIDKeyGenerateStrategy {

    int DEFAULT_MAX_KEY_SIZE_POLICY = UUID.randomUUID().toString().length();

    int DEFAULT_MIN_KEY_SIZE_POLICY = 10;

    /**
     * UUID 생성시 하이픈 갯수
     */
    int UUID_HYPHEN_SIZE = 4;

    int getKeySize();

    void setKeySize(int keySize);

    void checkKeySizePolicy(int keySize);

    String generateUUID();
}
