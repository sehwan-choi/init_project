package com.me.security.security.model;

public record JwtVerifyResult(Long id) implements VerifyResult{
    @Override
    public Long getId() {
        return this.id;
    }
}
