package com.me.security.externalkey.service;

import com.me.security.common.generator.UUIDKeyGenerator;
import com.me.security.externalkey.domain.ExternalKey;
import com.me.security.externalkey.dto.KeyRegistrationRequest;
import com.me.security.externalkey.dto.KeyRegistrationResponse;
import com.me.security.externalkey.exception.ApiKeyNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

class ExternalKeyManagementServiceTest {

    private final KeySelectQueryService keyQueryService = mock(KeySelectQueryService.class);
    private final DefinitionKeyCommandService keyCommandService = mock(DefinitionKeyCommandService.class);

    private final UUIDKeyGenerator generator = mock(UUIDKeyGenerator.class);

    ExternalKeyManagementService service = new ExternalKeyManagementService(keyQueryService, keyCommandService, generator);

    private static final String API_KEY = "12345";
    private static final String KEY_NAME = "NAME";

    private static final ExternalKey key = mock(ExternalKey.class);

    @Test
    @DisplayName("외부 키 등록 성공 테스트")
    void registrationSuccess() {
        when(generator.generate()).thenReturn(API_KEY);
        when(keyCommandService.save(any())).thenReturn(key);
        when(key.getApiKey()).thenReturn(API_KEY);

        KeyRegistrationRequest request = new KeyRegistrationRequest(KEY_NAME, null, null);
        KeyRegistrationResponse response = service.keyRegistration(request);

        verify(keyCommandService, times(1)).save(any());
        Assertions.assertThat(response.apiKey()).isEqualTo(API_KEY);
    }

    @Test
    @DisplayName("외부 키 Block 테스트")
    void keyBlock() {
        when(keyQueryService.findByKey(API_KEY)).thenReturn(Optional.of(key));

        service.keyDelete(API_KEY);

        verify(key, times(1)).block();
    }

    @Test
    @DisplayName("외부 키 Block 테스트 - 키를 찾을수 없는경우 ApiKeyNotFoundException 발생")
    void keyBlockNotFoundKey() {
        when(keyQueryService.findByKey(API_KEY)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.keyDelete(API_KEY)).isInstanceOf(ApiKeyNotFoundException.class);
    }
}