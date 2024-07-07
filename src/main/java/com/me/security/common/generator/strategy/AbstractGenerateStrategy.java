package com.me.security.common.generator.strategy;

import com.me.security.common.generator.exception.KeyGenerationPolicyViolationException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractGenerateStrategy implements UUIDKeyGenerateStrategy{

    protected final int maxKeySize;

    protected final int minKeySize;

    protected int keySizePolicy;

    @Override
    public int getKeySize() {
        return this.keySizePolicy;
    }

    @Override
    public void setKeySize(int keySize) {
        checkKeySizePolicy(keySize);
        this.keySizePolicy = keySize;
    }

    @Override
    public void checkKeySizePolicy(int keySize) {
        if (keySize < minKeySize || keySize > maxKeySize) {
            throw new KeyGenerationPolicyViolationException("Larger or smaller than the default key size [setKeySize : " + keySize + "] [minKeySize : " + minKeySize + "] [maxKeySize : " + maxKeySize + "]");
        }
    }

    @Override
    public String generateUUID() {
        String uuid = createUUID();

        return uuid.substring(0, keySizePolicy);
    }

    protected abstract String createUUID();
}
