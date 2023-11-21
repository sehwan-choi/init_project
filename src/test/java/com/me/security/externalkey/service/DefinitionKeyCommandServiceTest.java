package com.me.security.externalkey.service;

import com.me.security.common.generator.UUIDKeyGenerator;
import com.me.security.externalkey.domain.ExternalKey;
import com.me.security.externalkey.repository.ExternalKeyRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DefinitionKeyCommandServiceTest {

    private final ExternalKeyRepository repository = mock(ExternalKeyRepository.class);

    DefinitionKeyCommandService service = new DefinitionKeyCommandService(repository);

    private static final String KEY_NAME = "KEY";

    @Test
    @DisplayName("ExternalKey 저장 테스트")
    void save() {
        ExternalKey key = new ExternalKey(KEY_NAME, mock(UUIDKeyGenerator.class));
        when(repository.save(key)).thenReturn(key);
        ExternalKey saveKey = service.save(key);

        Assertions.assertThat(saveKey.getName()).isEqualTo(KEY_NAME);
    }
}