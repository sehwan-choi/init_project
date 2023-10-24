package com.me.security.security.jwt;

import com.me.security.security.service.TokenValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtTokenValidationService implements TokenValidationService {

    @Qualifier("jwtProvider")
    private final JwtService jwtService;

    @Override
    public boolean validation(String token) {
        return jwtService.validateToken(token);
    }

    @Override
    public Long getId(String token) {
        String userId = jwtService.getAccount(token);
        return Long.parseLong(userId);
    }
}
