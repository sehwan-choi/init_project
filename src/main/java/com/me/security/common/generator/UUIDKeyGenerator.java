package com.me.security.common.generator;

import lombok.Setter;

import java.util.UUID;

public class UUIDKeyGenerator implements KeyGenerator{

    private static final int DEFAULT_MAX_KEY_SIZE_POLICY = UUID.randomUUID().toString().length(); // 36

    private static final int UUID_HYPHEN_SIZE = 4;

    private static final int NO_HYPHEN_DEFAULT_MAX_KEY_SIZE_POLICY = DEFAULT_MAX_KEY_SIZE_POLICY - UUID_HYPHEN_SIZE; // 32

    private static final int DEFAULT_MIN_KEY_SIZE_POLICY = 10;

    private boolean hyphen = true;

    @Setter
    private boolean upper = false;

    private int keySizePolicy = DEFAULT_MAX_KEY_SIZE_POLICY;

    @Override
    public String generate() {
        return makeKey();
    }

    public void setHyphen(boolean isHyphen) {
        this.hyphen = isHyphen;
        checkKeySize();
    }

    public void setKeySizePolicy(int keySizePolicy) {
        this.keySizePolicy = keySizePolicy;
        checkKeySize();
    }

    private void checkKeySize() {
        if (this.keySizePolicy < DEFAULT_MIN_KEY_SIZE_POLICY) {
            this.keySizePolicy = DEFAULT_MIN_KEY_SIZE_POLICY;
        }
        else {
            if (this.hyphen) {
                if (this.keySizePolicy > DEFAULT_MAX_KEY_SIZE_POLICY) {
                    this.keySizePolicy = DEFAULT_MAX_KEY_SIZE_POLICY;
                }
            } else {
                if (this.keySizePolicy > NO_HYPHEN_DEFAULT_MAX_KEY_SIZE_POLICY) {
                    this.keySizePolicy = NO_HYPHEN_DEFAULT_MAX_KEY_SIZE_POLICY;
                }
            }
        }
    }

    private String makeKey() {
        String uuid = UUID.randomUUID().toString();

        String resultKey = this.hyphen ?
                uuid.substring(0, this.keySizePolicy) :
                uuid.replaceAll("-", "").substring(0, this.keySizePolicy);

        return upper ? resultKey.toUpperCase() : resultKey;
    }
}
