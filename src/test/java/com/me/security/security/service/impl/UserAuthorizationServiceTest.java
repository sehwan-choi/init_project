package com.me.security.security.service.impl;

import com.me.security.member.domain.Authority;
import com.me.security.member.domain.User;
import com.me.security.member.domain.UserAuthority;
import com.me.security.member.exception.UserNotFoundException;
import com.me.security.member.service.UserQueryService;
import com.me.security.security.exception.ClientNotFoundAuthenticationException;
import com.me.security.security.model.ClientAuthInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserAuthorizationServiceTest {

    private final UserQueryService queryService = mock(UserQueryService.class);

    private final UserAuthorizationService service = new UserAuthorizationService(queryService);

    private static final Long ID = 1L;

    private static final String NAME = "NAME";

    private static final Set<UserAuthority> ROLES = Set.of(new UserAuthority(Authority.USER, LocalDateTime.now()));

    @Test
    @DisplayName("ID 를 통해 사용자 정보를 가져온다")
    void getAccessClient() {
        User user = mock(User.class);
        when(queryService.findUserByIdIfNoOptional(ID)).thenReturn(user);
        when(user.getId()).thenReturn(ID);
        when(user.getName()).thenReturn(NAME);
        when(user.getRoles()).thenReturn(ROLES);

        ClientAuthInfo info = service.getAccessClient(ID);

        Assertions.assertThat(info.getId()).isEqualTo(ID);
        Assertions.assertThat(info.getName()).isEqualTo(NAME);
        Assertions.assertThat(info.getRoles()).isEqualTo(ROLES.stream().map(UserAuthority::getAuthority).collect(Collectors.toList()));
    }

    @Test
    @DisplayName("ID 를 통해 사용자 정보를 찾지 못한경우")
    void notFoundUser() {
        User user = mock(User.class);
        when(queryService.findUserByIdIfNoOptional(ID)).thenThrow(new UserNotFoundException(ID));

        Assertions.assertThatThrownBy(() -> service.getAccessClient(ID)).isInstanceOf(ClientNotFoundAuthenticationException.class);
    }
}