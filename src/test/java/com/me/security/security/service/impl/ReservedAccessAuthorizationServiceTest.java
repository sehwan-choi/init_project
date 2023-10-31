package com.me.security.security.service.impl;

import com.me.security.externalkey.dto.ExternalKeyAttribute;
import com.me.security.externalkey.exception.ApiKeyNotFoundException;
import com.me.security.security.exception.ClientNotFoundAuthenticationException;
import com.me.security.security.exception.ClientStatusAuthenticationException;
import com.me.security.security.model.ClientAuthInfo;
import com.me.security.security.service.ExternalKeyStorage;
import org.apiguardian.api.API;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservedAccessAuthorizationServiceTest {

    private final ExternalKeyStorage keyStorage = mock(ExternalKeyStorage.class);

    private final ReservedAccessAuthorizationService service = new ReservedAccessAuthorizationService(keyStorage);

    private static final Long ID = 1L;
    private static final String API_KEY = "KEY";
    private static final String NAME = "NAME";

    @Nested
    @DisplayName("ID 를 통해 외부키 정보를 가져온다")
    class AccessClient {

        @Test
        @DisplayName("정상 동작")
        void getAccessClient() {
            ExternalKeyAttribute attribute = new ExternalKeyAttribute(ID, API_KEY, NAME, false, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));

            when(keyStorage.findByIdIfNoOptional(ID)).thenReturn(attribute);

            ClientAuthInfo accessClient = service.getAccessClient(ID);

            Assertions.assertThat(accessClient.getId()).isEqualTo(attribute.id());
            Assertions.assertThat(accessClient.getName()).isEqualTo(attribute.name());
        }

        @Test
        @DisplayName("ID를 찾을 수 없는경우")
        void notFoundId() {
            when(keyStorage.findByIdIfNoOptional(ID)).thenThrow(new ApiKeyNotFoundException(ID));

            Assertions.assertThatThrownBy(() -> service.getAccessClient(ID)).isInstanceOf(ClientNotFoundAuthenticationException.class);
        }

        @Test
        @DisplayName("ID는 찾았지만 block 상태인경우")
        void blocked() {
            ExternalKeyAttribute attribute = new ExternalKeyAttribute(ID, API_KEY, NAME, true, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));

            when(keyStorage.findByIdIfNoOptional(ID)).thenReturn(attribute);


            Assertions.assertThatThrownBy(() -> service.getAccessClient(ID)).isInstanceOf(ClientStatusAuthenticationException.class);
        }

        @Test
        @DisplayName("ID는 찾았지만 Expired 상태인경우")
        void expired() {
            ExternalKeyAttribute attribute = new ExternalKeyAttribute(ID, API_KEY, NAME, true, LocalDateTime.now().minusDays(5), LocalDateTime.now().minusDays(2));

            when(keyStorage.findByIdIfNoOptional(ID)).thenReturn(attribute);

            Assertions.assertThatThrownBy(() -> service.getAccessClient(ID)).isInstanceOf(ClientStatusAuthenticationException.class);
        }
    }
}