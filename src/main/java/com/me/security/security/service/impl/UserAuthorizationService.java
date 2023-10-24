package com.me.security.security.service.impl;

import com.me.security.member.domain.User;
import com.me.security.member.domain.UserAuthority;
import com.me.security.member.exception.UserNotFoundException;
import com.me.security.member.service.UserQueryService;
import com.me.security.security.exception.ClientNotFountAuthenticationException;
import com.me.security.security.model.ClientAuthInfo;
import com.me.security.security.model.UserClientAuthInfo;
import com.me.security.security.service.AccessAuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserAuthorizationService implements AccessAuthorizationService {

    private final UserQueryService userQueryService;

    @Override
    public ClientAuthInfo getAccessClient(Long id) {
        try {
            User user = userQueryService.findUserById(id);
            return UserClientAuthInfo.builder()
                    .userId(user.getId())
                    .userName(user.getName())
                    .roles(user.getRoles().stream().map(UserAuthority::getAuthority).collect(Collectors.toList()))
                    .build();
        } catch (UserNotFoundException e) {
            throw new ClientNotFountAuthenticationException("user Id \"" + id + "\" not found!");
        }
    }
}
