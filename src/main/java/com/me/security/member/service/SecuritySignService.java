package com.me.security.member.service;

import com.me.security.member.domain.User;
import com.me.security.member.dto.LoginRequest;
import com.me.security.member.dto.LoginSuccessResponse;
import com.me.security.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecuritySignService implements SignService{

    private final UserQueryService userQueryService;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    @Override
    public LoginSuccessResponse login(LoginRequest request) {
        User user = userQueryService.findUserByUserName(request.email());
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadCredentialsException("Input password does not match stored password");
        }
        return new LoginSuccessResponse(jwtProvider.createToken(String.valueOf(user.getId()), user.getRoles()));
    }
}
