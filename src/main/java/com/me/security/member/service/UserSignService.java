package com.me.security.member.service;

import com.me.security.member.domain.User;
import com.me.security.member.dto.LoginRequest;
import com.me.security.member.dto.LoginSuccessResponse;
import com.me.security.member.dto.UserCreateRequest;
import com.me.security.member.dto.UserCreateResponse;
import com.me.security.member.exception.UserEmailDuplicateException;
import com.me.security.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserSignService implements SignService {

    private final UserQueryService userQueryService;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    private final UserCommandService userCommandService;

    @Override
    @Transactional(readOnly = true)
    public LoginSuccessResponse login(LoginRequest request) {
        User user = userQueryService.findUserByEmailIfNoOptional(request.email());
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadCredentialsException("Input password does not match stored password");
        }
        return new LoginSuccessResponse(jwtProvider.createToken(String.valueOf(user.getId()), user.getAuthorities()));
    }

    @Override
    public UserCreateResponse signup(UserCreateRequest request) {
        String encodePassword = passwordEncoder.encode(request.password());
        User user = userCommandService.create(new UserCreateRequest(request.userName(), encodePassword, request.email()));
        return new UserCreateResponse(user.getEmail(), user.getName(), user.getCreatedAt());
    }
}
