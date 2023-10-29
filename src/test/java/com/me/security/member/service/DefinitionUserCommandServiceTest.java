package com.me.security.member.service;

import com.me.security.member.domain.User;
import com.me.security.member.dto.UserCreateRequest;
import com.me.security.member.exception.UserEmailDuplicateException;
import com.me.security.member.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DefinitionUserCommandServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);

    DefinitionUserCommandService service = new DefinitionUserCommandService(userRepository);

    private final User user = mock(User.class);

    private static final String USER_EMAIL = "EMAIL";

    @Test
    @DisplayName("유저 생성 테스트")
    void userCreate() {
        UserCreateRequest request = mock(UserCreateRequest.class);
        when(request.email()).thenReturn(USER_EMAIL);
        when(userRepository.save(any())).thenReturn(user);

        User createUser = service.create(request);
        Assertions.assertThat(createUser).isEqualTo(user);
    }

    @Test
    @DisplayName("중복된 이메일이 있는경우 실패")
    void duplicateEmail() {
        UserCreateRequest request = mock(UserCreateRequest.class);
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(mock(User.class)));
        when(request.email()).thenReturn(USER_EMAIL);
        Assertions.assertThatThrownBy(() -> service.create(request)).isInstanceOf(UserEmailDuplicateException.class);
    }
}