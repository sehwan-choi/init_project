package com.me.security.security.service.impl;

import com.me.security.security.model.VerifyResult;
import com.me.security.security.service.TokenValidationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtAuthenticationTokenVerifierTest {

    private final TokenValidationService service = mock(TokenValidationService.class);

    JwtAuthenticationTokenVerifier verifier = new JwtAuthenticationTokenVerifier(service);

    private static final String TOKEN = "TOKEN";
    private static final Long USER_ID = 1L;


    @Test
    @DisplayName("JWT 토큰 검증 성공")
    void jwtTokenValidation() {
        when(service.validation(TOKEN)).thenReturn(true);
        when(service.getId(TOKEN)).thenReturn(USER_ID);

        Optional<VerifyResult> verify = verifier.verify(TOKEN);

        Assertions.assertThat(verify.isPresent()).isTrue();
        Assertions.assertThat(verify.get().getId()).isEqualTo(USER_ID);
    }

    @Test
    @DisplayName("JWT 토큰 검증 실패")
    void jwtTokenInValidation() {
        when(service.validation(TOKEN)).thenReturn(false);
        Optional<VerifyResult> verify = verifier.verify(TOKEN);

        Assertions.assertThat(verify.isPresent()).isFalse();
    }

}