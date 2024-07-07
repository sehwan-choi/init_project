package com.me.security.common.generator;

import com.me.security.common.generator.strategy.DefaultGenerateStrategy;
import com.me.security.common.generator.strategy.NoHyphenGenerateStrategy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class UUIDKeyGeneratorTest {

    @Nested
    @DisplayName("upper 테스트")
    class generate_upper {

        UUIDKeyGenerator uuidKeyGenerator;

        @BeforeEach
        void beforeEach() {
            uuidKeyGenerator = new UUIDKeyGenerator(new DefaultGenerateStrategy());
        }

        @Test
        @DisplayName("upper 기본값 테스트 - lower_case")
        void lowerCase() {
            String key = uuidKeyGenerator.generate();
            Assertions.assertThat(key).isLowerCase();
        }

        @Test
        @DisplayName("upper 변경 테스트 - upper_case")
        void upperCase() {
            uuidKeyGenerator.setUpper(true);
            String key = uuidKeyGenerator.generate();
            Assertions.assertThat(key).isUpperCase();
        }
    }

    @Nested
    @DisplayName("DefaultGenerateStrategy 테스트")
    class defaultGenerateStrategy {

        UUIDKeyGenerator uuidKeyGenerator;

        @BeforeEach
        void beforeEach() {
            uuidKeyGenerator = new UUIDKeyGenerator(new DefaultGenerateStrategy());
        }

        @Test
        @DisplayName("기본 Key Generator 성공 테스트")
        void success() {
            String key = uuidKeyGenerator.generate();

            Assertions.assertThat(key).hasSize(36);
            Assertions.assertThat(key.chars().filter(ch -> ch == '-').count()).isEqualTo(4);
        }
    }

    @Nested
    @DisplayName("NoHyphenGenerateStrategy 테스트")
    class noHyphenGenerateStrategy {

        UUIDKeyGenerator uuidKeyGenerator;

        @BeforeEach
        void beforeEach() {
            uuidKeyGenerator = new UUIDKeyGenerator(new NoHyphenGenerateStrategy());
        }

        @Test
        @DisplayName("기본 Key Generator 성공 테스트")
        void success() {
            String key = uuidKeyGenerator.generate();

            Assertions.assertThat(key).hasSize(32);
            Assertions.assertThat(key.chars().filter(ch -> ch == '-').count()).isEqualTo(0);
        }
    }

}