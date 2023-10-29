package com.me.security.externalkey.domain;

import com.me.security.externalkey.service.UUIDKeyGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class ExternalKeyTest {

    private static final String KEY_NAME = "TEST_KEY";

    @Nested
    @DisplayName("외부 키 기간 테스트")
    class keyDuration {

        private static final Long KEY_DEFAULT_DURATION = 1L;
        private static final LocalDateTime DURATION_START_TIME = LocalDateTime.now();
        private static final LocalDateTime DURATION_END_TIME = LocalDateTime.now().plusDays(7);
        @Test
        @DisplayName("외부 키 기간 미설정시 기본 기간으로 설정되는지 테스트")
        void keyDurationDefaultConstructorTest() {
            ExternalKey testKey = new ExternalKey(KEY_NAME, mock(UUIDKeyGenerator.class));

            LocalDateTime startDate = testKey.getStartDate();
            LocalDateTime endDate = testKey.getEndDate();

            Assertions.assertThat(startDate.toLocalDate()).isEqualTo(LocalDate.now());
            Assertions.assertThat(endDate.toLocalDate()).isEqualTo(LocalDate.now().plusDays(KEY_DEFAULT_DURATION));
        }

        @Test
        @DisplayName("외부 키 기간 startTime = null 설정시 기본 기간으로 설정되는지 테스트")
        void keyDurationStartTimeIsNullTest() {
            ExternalKey testKey = new ExternalKey(KEY_NAME, DURATION_START_TIME, null, mock(UUIDKeyGenerator.class));

            LocalDateTime startDate = testKey.getStartDate();
            LocalDateTime endDate = testKey.getEndDate();

            Assertions.assertThat(startDate.toLocalDate()).isEqualTo(LocalDate.now());
            Assertions.assertThat(endDate.toLocalDate()).isEqualTo(LocalDate.now().plusDays(KEY_DEFAULT_DURATION));
        }

        @Test
        @DisplayName("외부 키 기간 endTime = null 설정시 기본 기간으로 설정되는지 테스트")
        void keyDurationEndTimeIsNullTest() {
            ExternalKey testKey = new ExternalKey(KEY_NAME, null, DURATION_END_TIME, mock(UUIDKeyGenerator.class));

            LocalDateTime startDate = testKey.getStartDate();
            LocalDateTime endDate = testKey.getEndDate();

            Assertions.assertThat(startDate.toLocalDate()).isEqualTo(LocalDate.now());
            Assertions.assertThat(endDate.toLocalDate()).isEqualTo(LocalDate.now().plusDays(KEY_DEFAULT_DURATION));
        }

        @Test
        @DisplayName("외부 키 기간 설정시 테스트")
        void keyDurationTimeNotNullTest() {
            ExternalKey testKey = new ExternalKey(KEY_NAME, DURATION_START_TIME, DURATION_END_TIME, mock(UUIDKeyGenerator.class));

            LocalDateTime startDate = testKey.getStartDate();
            LocalDateTime endDate = testKey.getEndDate();

            Assertions.assertThat(DURATION_START_TIME).isEqualTo(startDate);
            Assertions.assertThat(DURATION_END_TIME).isEqualTo(endDate);
        }

        @Test
        @DisplayName("외부 키 기간 설정시 endDate가 startDate보다 빠른경우")
        void keyDurationEndDateIsAfterStartDate() {
            ExternalKey testKey = new ExternalKey(KEY_NAME, DURATION_END_TIME, DURATION_START_TIME, mock(UUIDKeyGenerator.class));

            LocalDateTime startDate = testKey.getStartDate();
            LocalDateTime endDate = testKey.getEndDate();

            Assertions.assertThat(startDate.toLocalDate()).isEqualTo(LocalDate.now());
            Assertions.assertThat(endDate.toLocalDate()).isEqualTo(LocalDate.now().plusDays(KEY_DEFAULT_DURATION));
        }

        @Test
        @DisplayName("외부 키 기간 설정시 startDate가 endDate보다 느린경우")
        void keyDurationStartDateIsBeforeEndDate() {
            ExternalKey testKey = new ExternalKey(KEY_NAME, LocalDateTime.now().plusDays(20), DURATION_END_TIME, mock(UUIDKeyGenerator.class));

            LocalDateTime startDate = testKey.getStartDate();
            LocalDateTime endDate = testKey.getEndDate();

            Assertions.assertThat(startDate.toLocalDate()).isEqualTo(LocalDate.now());
            Assertions.assertThat(endDate.toLocalDate()).isEqualTo(LocalDate.now().plusDays(KEY_DEFAULT_DURATION));
        }
    }

    @Nested
    @DisplayName("외부 키 Block 테스트")
    class block {
        @Test
        @DisplayName("외부 키 Block = true 테스트")
        void keyIsBlock() {

            ExternalKey testKey = new ExternalKey(KEY_NAME, mock(UUIDKeyGenerator.class));
            testKey.block();

            Assertions.assertThat(testKey.isBlock()).isTrue();
        }

        @Test
        @DisplayName("외부 키 생성시 block 테스트")
        void keyCreateTest() {

            ExternalKey testKey = new ExternalKey(KEY_NAME, mock(UUIDKeyGenerator.class));

            Assertions.assertThat(testKey.isBlock()).isFalse();
        }
    }
}