package com.me.security.security.jwt;

import com.me.security.member.domain.Authority;
import io.jsonwebtoken.MalformedJwtException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class JwtProviderTest {

    private final String saltKey = "31239jqf90ja0ff0j3902akjalkjrlk223a9i0fak2390fka9203kf0923";

    private final long expiredTime = 3600000;

    private final JwtProvider provider = new JwtProvider(saltKey, expiredTime);

    private final String authHeaderName = "auth";

    private final String authPrefixName = "prefix ";

    private static final String TEST_ACCOUNT = "1";

    private static final List<Authority> roles = List.of(Authority.USER);

    private final String invalidToken = "jf09a23j09a2jfaskljfklw";

    @BeforeEach
    void beforeEach() {
        provider.setAuthorizationHeaderName(authHeaderName);
        provider.setAuthorizationPrefixName(authPrefixName);
        provider.init();
    }

    private void init() {

    }

    @Test
    @DisplayName("정상 토큰 발급")
    void createToken() {
        String jwtToken = provider.createToken(TEST_ACCOUNT, roles);
        boolean validate = provider.validateToken(jwtToken);

        Assertions.assertThat(validate).isTrue();
    }

    @Test
    @DisplayName("토큰에서 userId 추출")
    void getAccount() {
        String jwtToken = provider.createToken(TEST_ACCOUNT, roles);
        String account = provider.getAccount(jwtToken);

        Assertions.assertThat(account).isEqualTo(TEST_ACCOUNT);
    }

    @Test
    @DisplayName("토큰에서 userId 추출시 잘못된 토큰인경우")
    void getAccountAndInvalidToken() {
        Assertions.assertThatThrownBy(() -> provider.getAccount(authPrefixName + invalidToken)).isInstanceOf(MalformedJwtException.class);
    }

    @Test
    @DisplayName("토큰 검증 실패 토큰만료")
    void invalidToken() {
        JwtProvider provider = new JwtProvider(saltKey, 0);
        provider.setAuthorizationHeaderName(authHeaderName);
        provider.setAuthorizationPrefixName(authPrefixName);
        provider.init();

        String jwtToken = provider.createToken(TEST_ACCOUNT, roles);

        boolean validate = provider.validateToken(jwtToken);
        Assertions.assertThat(validate).isFalse();
    }

    @Test
    @DisplayName("bearer prefix가 없는 토큰 검증 실패")
    void noBearerPrefixToken() {
        String jwtToken = provider.createToken(TEST_ACCOUNT, roles);

        boolean validate = provider.validateToken(invalidToken);
        Assertions.assertThat(validate).isFalse();
    }
}