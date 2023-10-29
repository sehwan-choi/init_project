package com.me.security.security.service.impl;

import com.me.security.externalkey.dto.ExternalKeyAttribute;
import com.me.security.security.model.ReservedVerifyResult;
import com.me.security.security.model.VerifyResult;
import com.me.security.security.service.AuthenticationTokenVerifier;
import com.me.security.security.service.ExternalKeyStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservedAuthenticationTokenVerifier implements AuthenticationTokenVerifier {

    private final ExternalKeyStorage storage;

    @Override
    public Optional<VerifyResult> verify(String token) {
        Optional<ExternalKeyAttribute> optionalKey = storage.findByKey(token);
        return optionalKey.map(externalKey -> new ReservedVerifyResult(externalKey.id()));
    }
}
