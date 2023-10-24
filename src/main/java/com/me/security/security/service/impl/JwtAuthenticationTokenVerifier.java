package com.me.security.security.service.impl;

import com.me.security.security.model.JwtVerifyResult;
import com.me.security.security.model.VerifyResult;
import com.me.security.security.service.AuthenticationTokenVerifier;
import com.me.security.security.service.TokenValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationTokenVerifier implements AuthenticationTokenVerifier {

    private final TokenValidationService tokenValidationService;

    @Override
    public Optional<VerifyResult> verify(String token) {
        if (tokenValidationService.validation(token)) {
            Long userId = tokenValidationService.getId(token);
            return Optional.of(new JwtVerifyResult(userId));
        }
        return Optional.empty();
    }
}
