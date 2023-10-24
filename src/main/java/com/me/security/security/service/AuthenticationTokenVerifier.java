package com.me.security.security.service;

import com.me.security.security.model.VerifyResult;

import java.util.Optional;

public interface AuthenticationTokenVerifier {

    Optional<VerifyResult> verify(String token);
}
