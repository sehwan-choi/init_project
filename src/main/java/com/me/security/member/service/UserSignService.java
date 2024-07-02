package com.me.security.member.service;

import com.me.security.member.domain.User;
import com.me.security.member.dto.LoginRequest;
import com.me.security.member.dto.LoginResult;
import com.me.security.member.dto.UserCreateRequest;
import com.me.security.member.dto.UserCreateResponse;
import com.me.security.member.exception.LoginException;
import com.me.security.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserSignService implements SignService {

    private final UserQueryService userQueryService;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    private final UserCommandService userCommandService;

    @Override
    public LoginResult login(LoginRequest request) {
        User user = userQueryService.findUserByEmailIfNoOptional(request.email());
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new LoginException("password mismatch! userId : " + user.getId());
        }
        return new LoginResult(jwtProvider.createToken(String.valueOf(user.getId()), user.getAuthorities()));
    }

    @Override
    public UserCreateResponse signup(UserCreateRequest request) {
        String encodePassword = passwordEncoder.encode(request.password());
        User user = userCommandService.create(new UserCreateRequest(request.userName(), encodePassword, request.email()));
        return new UserCreateResponse(user.getEmail(), user.getName(), user.getCreatedAt());
    }
}
