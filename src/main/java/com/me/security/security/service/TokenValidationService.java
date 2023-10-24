package com.me.security.security.service;

public interface TokenValidationService {

    boolean validation(String token);

    Long getId(String token);
}
