package com.me.security.common.generator.strategy;

import com.me.security.common.generator.exception.KeyGenerationPolicyViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoHyphenGenerateStrategyTest {

    NoHyphenGenerateStrategy strategy;

    @BeforeEach
    void beforeEach() {
        strategy = new NoHyphenGenerateStrategy();
    }

    @Test
    @DisplayName("기본 Key Generator 성공 테스트")
    void noHyphenGenerateStrategy() {
        String key = strategy.generateUUID();

        Assertions.assertThat(key).hasSize(32);
        Assertions.assertThat(key.chars().filter(ch -> ch == '-').count()).isEqualTo(0);
    }

    @Test
    @DisplayName("기본 Key Generator MIN Length 변경 테스트")
    void strategy_key_min_length_change() {
        strategy.setKeySize(10);
        String key = strategy.generateUUID();

        Assertions.assertThat(key).hasSize(10);
    }

    @Test
    @DisplayName("기본 Key Generator Length 변경 테스트")
    void strategy_key_length_change() {
        strategy.setKeySize(20);
        String key = strategy.generateUUID();

        Assertions.assertThat(key).hasSize(20);
    }

    @Test
    @DisplayName("기본 Key Generator MIN 값보다 낮게 설정하려는 경우 테스트")
    void strategy_key_length_smaller_than() {
        Assertions.assertThatThrownBy(() -> strategy.setKeySize(5)).isInstanceOf(KeyGenerationPolicyViolationException.class);
    }

    @Test
    @DisplayName("기본 Key Generator MAX 값보다 높게 설정하려는 경우 테스트")
    void strategy_key_length_larger_than() {
        Assertions.assertThatThrownBy(() -> strategy.setKeySize(100)).isInstanceOf(KeyGenerationPolicyViolationException.class);
    }

}