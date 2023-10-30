package com.me.security.security.service.impl;

import com.me.security.externalkey.domain.ExternalKey;
import com.me.security.externalkey.dto.ExternalKeyAttribute;
import com.me.security.externalkey.exception.ApiKeyNotFoundException;
import com.me.security.externalkey.service.KeyQueryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InMemoryExternalKeyStorageTest {

    private final KeyQueryService keyQueryService = mock(KeyQueryService.class);
    private final InMemoryExternalKeyStorage storage = new InMemoryExternalKeyStorage(keyQueryService);


    private List<ExternalKey> keys;

    private final static Long EXIST_TEST_ID = 1L;
    private final static Long NOT_EXIST_TEST_ID = 999L;

    @BeforeEach
    void beforeEach() {
        ExternalKey key1 = new ExternalKey(1L, "111", "name1", false, LocalDateTime.now(), LocalDateTime.now().plusDays(1), LocalDateTime.now(), LocalDateTime.now());
        ExternalKey key2 = new ExternalKey(2L, "222", "name2", false, LocalDateTime.now(), LocalDateTime.now().plusDays(1), LocalDateTime.now(), LocalDateTime.now());
        ExternalKey key3 = new ExternalKey(3L, "333", "name3", false, LocalDateTime.now(), LocalDateTime.now().plusDays(1), LocalDateTime.now(), LocalDateTime.now());
        ExternalKey key4 = new ExternalKey(4L, "444", "name4", false, LocalDateTime.now(), LocalDateTime.now().plusDays(1), LocalDateTime.now(), LocalDateTime.now());
        ExternalKey key5 = new ExternalKey(5L, "555", "name5", false, LocalDateTime.now(), LocalDateTime.now().plusDays(1), LocalDateTime.now(), LocalDateTime.now());

        keys = List.of(key1, key2, key3, key4, key5);
        when(keyQueryService.findAllKey()).thenReturn(keys);
        storage.load();
    }

    @Nested
    @DisplayName("외부 키 load / reload 테스트")
    class loadTest {

        @Test
        @DisplayName("외부 키 load 정상 테스트")
        void loadTest() {
            ExternalKeyAttribute keyAttribute1 = storage.findByIdIfNoOptional(1L);
            ExternalKeyAttribute keyAttribute2 = storage.findByIdIfNoOptional(2L);
            ExternalKeyAttribute keyAttribute3 = storage.findByIdIfNoOptional(3L);
            ExternalKeyAttribute keyAttribute4 = storage.findByIdIfNoOptional(4L);
            ExternalKeyAttribute keyAttribute5 = storage.findByIdIfNoOptional(5L);

            Assertions.assertThat(keyAttribute1.apiKey()).isEqualTo("111");
            Assertions.assertThat(keyAttribute2.apiKey()).isEqualTo("222");
            Assertions.assertThat(keyAttribute3.apiKey()).isEqualTo("333");
            Assertions.assertThat(keyAttribute4.apiKey()).isEqualTo("444");
            Assertions.assertThat(keyAttribute5.apiKey()).isEqualTo("555");
        }

        @Test
        @DisplayName("외부 키 reload 정상 테스트")
        void reloadTest() {
            ExternalKey key1 = new ExternalKey(1L, "111", "name1", false, LocalDateTime.now(), LocalDateTime.now().plusDays(1), LocalDateTime.now(), LocalDateTime.now());
            ExternalKey key3 = new ExternalKey(3L, "333", "name3", false, LocalDateTime.now(), LocalDateTime.now().plusDays(1), LocalDateTime.now(), LocalDateTime.now());
            ExternalKey key5 = new ExternalKey(5L, "555", "name5", false, LocalDateTime.now(), LocalDateTime.now().plusDays(1), LocalDateTime.now(), LocalDateTime.now());

            List<ExternalKey> keys = List.of(key1, key3, key5);
            when(keyQueryService.findAllKey()).thenReturn(keys);

            storage.reload();
            Optional<ExternalKeyAttribute> keyAttribute1 = storage.findById(1L);
            Optional<ExternalKeyAttribute> keyAttribute2 = storage.findById(2L);
            Optional<ExternalKeyAttribute> keyAttribute3 = storage.findById(3L);
            Optional<ExternalKeyAttribute> keyAttribute4 = storage.findById(4L);
            Optional<ExternalKeyAttribute> keyAttribute5 = storage.findById(5L);

            Assertions.assertThat(keyAttribute1.isPresent()).isTrue();
            Assertions.assertThat(keyAttribute2.isPresent()).isFalse();
            Assertions.assertThat(keyAttribute3.isPresent()).isTrue();
            Assertions.assertThat(keyAttribute4.isPresent()).isFalse();
            Assertions.assertThat(keyAttribute5.isPresent()).isTrue();
        }
    }

    @Nested
    @DisplayName("findById() 테스트")
    class findByIdTest {
        @Test
        @DisplayName("findById() 외부 키 ID로 KeyAttibute 정상적으로 찾아오기")
        void findById() {
            Optional<ExternalKeyAttribute> attribute = storage.findById(EXIST_TEST_ID);

            Assertions.assertThat(attribute.isPresent()).isTrue();
            Assertions.assertThat(attribute.get().id()).isEqualTo(EXIST_TEST_ID);
        }

        @Test
        @DisplayName("findById() 외부 키 ID로 KeyAttibute 못찾은경우")
        void findByIdNotFound() {
            Optional<ExternalKeyAttribute> attribute = storage.findById(NOT_EXIST_TEST_ID);

            Assertions.assertThat(attribute.isPresent()).isFalse();
        }
    }

    @Nested
    @DisplayName("findByIdIfNoOptional() 테스트")
    class findByIdIfNoOptionalTest {
        @Test
        @DisplayName("findByIdIfNoOptional() 외부 키 ID로 KeyAttibute 정상적으로 찾아오기")
        void findByIdIfNoOptional() {
            ExternalKeyAttribute attribute = storage.findByIdIfNoOptional(EXIST_TEST_ID);

            Assertions.assertThat(attribute.id()).isEqualTo(EXIST_TEST_ID);
        }

        @Test
        @DisplayName("findByIdIfNoOptional() 외부 키 ID로 KeyAttibute 못찾은경우")
        void findByIdIfNoOptionalNotFound() {
            Assertions.assertThatThrownBy(() -> storage.findByIdIfNoOptional(NOT_EXIST_TEST_ID)).isInstanceOf(ApiKeyNotFoundException.class);
        }
    }
}