package com.me.security.security.service.impl;

import com.me.security.externalkey.dto.ExternalKeyAttribute;
import com.me.security.security.model.VerifyResult;
import com.me.security.security.service.ExternalKeyStorage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservedAuthenticationTokenVerifierTest {

    private final ExternalKeyStorage storage = mock(ExternalKeyStorage.class);

    ReservedAuthenticationTokenVerifier verifier = new ReservedAuthenticationTokenVerifier(storage);

    private static final String TOKEN = "TOKEN";
    private static final Long ID = 1L;
    private static final String API_KEY = "KEY";
    private static final String NAME = "NAME";

    @Test
    @DisplayName("외부에서 API 호출시 토큰 검증 성공")
    void tokenVerify() {
        ExternalKeyAttribute attribute = new ExternalKeyAttribute(ID, API_KEY, NAME, false, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));
        when(storage.findByKey(TOKEN)).thenReturn(Optional.of(attribute));
        Optional<VerifyResult> verify = verifier.verify(TOKEN);

        Assertions.assertThat(verify.isPresent()).isTrue();
        Assertions.assertThat(verify.get().getId()).isEqualTo(ID);
    }

    @Test
    @DisplayName("외부에서 API 호출시 토큰 검증 실패(기간 만료, 잘못된 토큰)")
    void tokenVerifyInvalid() {
        when(storage.findByKey(TOKEN)).thenReturn(Optional.empty());
        Optional<VerifyResult> verify = verifier.verify(TOKEN);

        Assertions.assertThat(verify.isPresent()).isFalse();
    }

}