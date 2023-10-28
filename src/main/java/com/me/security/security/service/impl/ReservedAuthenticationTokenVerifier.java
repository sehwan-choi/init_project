package com.me.security.security.service.impl;

import com.me.security.externalkey.domain.ExternalKey;
import com.me.security.externalkey.service.KeyQueryService;
import com.me.security.security.model.ReservedVerifyResult;
import com.me.security.security.model.VerifyResult;
import com.me.security.security.service.AuthenticationTokenVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservedAuthenticationTokenVerifier implements AuthenticationTokenVerifier {

    private final KeyQueryService service;

    @Override
    public Optional<VerifyResult> verify(String token) {
        Optional<ExternalKey> optionalKey = service.findByKey(token);
        return optionalKey.map(externalKey -> new ReservedVerifyResult(externalKey.getId()));
    }
}
