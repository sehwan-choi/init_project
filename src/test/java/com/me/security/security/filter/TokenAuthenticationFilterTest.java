package com.me.security.security.filter;

import com.me.security.security.model.ClientAuthInfo;
import com.me.security.security.model.ClientAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import static org.mockito.Mockito.*;

class TokenAuthenticationFilterTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final AuthenticationManager manager = mock(AuthenticationManager.class);
    private final TokenAuthenticationFilter filter = new TokenAuthenticationFilter(manager);

    private final static String AUTH_NAME = "auth";
    private final static String TOKEN = "token";

    private final ClientAuthInfo clientAuthInfo = mock(ClientAuthInfo.class);

    private final ClientAuthenticationToken clientToken = new ClientAuthenticationToken(clientAuthInfo, TOKEN);
    @BeforeEach
    void beforeEach() {
        filter.setTokenAuthorizationHeaderName(AUTH_NAME);
    }

    @Test
    @DisplayName("Token Filter 정상 동작")
    void tokenFilterSuccess() throws ServletException, IOException {
        when(request.getHeader(AUTH_NAME)).thenReturn(TOKEN);
        when(manager.authenticate(any())).thenReturn(clientToken);

        filter.doFilterInternal(request, mock(HttpServletResponse.class), mock(FilterChain.class));

        ClientAuthenticationToken clientAuthenticationToken = (ClientAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Assertions.assertThat(clientAuthenticationToken.getAccessToken()).isEqualTo(TOKEN);
    }

    @Test
    @DisplayName("Token이 없는경우")
    void notFoundToken() {
        when(request.getHeader(AUTH_NAME)).thenReturn(null);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Assertions.assertThat(authentication).isNull();
    }
}