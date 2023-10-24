package com.me.security.security.model;

public record ReservedVerifyResult(Long id) implements VerifyResult{

    @Override
    public Long getId() {
        return null;
    }
}
