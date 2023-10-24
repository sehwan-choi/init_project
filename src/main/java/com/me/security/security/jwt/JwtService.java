package com.me.security.security.jwt;

import com.me.security.member.domain.Authority;

import java.util.Collection;

public interface JwtService {

    String createToken(String account, Collection<Authority> roles);

    String getAccount(String token);

    boolean validateToken(String token);
}
