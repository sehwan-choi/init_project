package com.me.security.common.generator.exception;

import com.me.security.common.code.ServerCode;
import com.me.security.common.exception.InvalidDataException;

public class KeyGenerationPolicyViolationException extends InvalidDataException {

    public KeyGenerationPolicyViolationException(String message) {
        super(ServerCode.KEY_GENERATION_FAIL, message);
    }
}
