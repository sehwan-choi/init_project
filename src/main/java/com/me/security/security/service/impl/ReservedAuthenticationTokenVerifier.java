package com.me.security.security.service.impl;

import com.me.security.security.model.ReservedVerifyResult;
import com.me.security.security.model.VerifyResult;
import com.me.security.security.service.AuthenticationTokenVerifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReservedAuthenticationTokenVerifier implements AuthenticationTokenVerifier {

    private static final String TEST_UUID = "57907621-118c-443d-bad8-2b7cb2cbe780";
    private static final Long TEST_ID = 1L;

    @Override
    public Optional<VerifyResult> verify(String token) {
        if (TEST_UUID.equals(token)) {
            return Optional.of(new ReservedVerifyResult(TEST_ID));
        }
        return Optional.empty();
    }
}
