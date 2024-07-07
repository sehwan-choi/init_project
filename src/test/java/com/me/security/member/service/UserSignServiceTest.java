package com.me.security.member.service;

import com.me.security.member.domain.User;
import com.me.security.member.dto.LoginRequest;
import com.me.security.member.dto.LoginResult;
import com.me.security.member.dto.UserCreateRequest;
import com.me.security.member.dto.UserCreateResponse;
import com.me.security.member.exception.LoginException;
import com.me.security.security.jwt.JwtProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;

class UserSignServiceTest {

    private final UserQueryService userQueryService = mock(UserQueryService.class);

    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

    private final JwtProvider jwtProvider = mock(JwtProvider.class);

    private final UserCommandService userCommandService = mock(UserCommandService.class);

    UserSignService service = new UserSignService(userQueryService, passwordEncoder, jwtProvider, userCommandService);

    private static final String EMAIL = "EMAIL";
    private static final String PASSWORD = "1234";
    private final User user = mock(User.class);

    @Nested
    @DisplayName("로그인")
    class login {
        private static final String WRONG_PASSWORD = "WRONG";
        private static final String JWT_TOKEN = "TOKEN";
        @Test
        @DisplayName("로그인 성공")
        void login() {
            LoginRequest request = new LoginRequest(EMAIL, PASSWORD);
            when(userQueryService.findUserByEmailIfNoOptional(EMAIL)).thenReturn(user);
            when(user.getPassword()).thenReturn(PASSWORD);
            when(jwtProvider.createToken(any(), any())).thenReturn(JWT_TOKEN);
            when(passwordEncoder.matches(request.password(), PASSWORD)).thenReturn(true);

            LoginResult response = service.login(request);
            verify(passwordEncoder, times(1)).matches(request.password(), PASSWORD);
            Assertions.assertThat(response.token()).isEqualTo(JWT_TOKEN);
        }

        @Test
        @DisplayName("패스워드 불일치")
        void invalidPassword() {
            LoginRequest request = new LoginRequest(EMAIL, WRONG_PASSWORD);
            when(userQueryService.findUserByEmailIfNoOptional(EMAIL)).thenReturn(user);
            when(user.getPassword()).thenReturn(PASSWORD);
            when(passwordEncoder.matches(request.password(), PASSWORD)).thenReturn(false);

            Assertions.assertThatThrownBy(() -> service.login(request)).isInstanceOf(LoginException.class);
        }
    }

    @Nested
    @DisplayName("회원 가입")
    class signUp {

        private static final String USERNAME = "NAME";
        private static final String ENCODE_PASSWORD = "1@2@";

        @Test
        @DisplayName("회원 가입")
        void signUp() {
            when(passwordEncoder.encode(PASSWORD)).thenReturn(ENCODE_PASSWORD);
            when(userCommandService.create(any())).thenReturn(user);
            when(user.getEmail()).thenReturn(EMAIL);
            when(user.getName()).thenReturn(USERNAME);
            UserCreateRequest request = new UserCreateRequest(USERNAME, PASSWORD, EMAIL);

            UserCreateResponse response = service.signup(request);

            verify(userCommandService, times(1)).create(any());
            Assertions.assertThat(response.email()).isEqualTo(EMAIL);
            Assertions.assertThat(response.userName()).isEqualTo(USERNAME);
        }
    }
}