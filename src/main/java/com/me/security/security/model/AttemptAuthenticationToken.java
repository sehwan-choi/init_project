package com.me.security.security.model;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class AttemptAuthenticationToken extends AbstractAuthenticationToken {

    @Getter
    private String accessToken;

    public AttemptAuthenticationToken(String accessToken) {
        super(null);
        this.accessToken = accessToken;
        setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return this.accessToken;
    }

    @Override
    public Object getPrincipal() {
        return this.accessToken;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.accessToken = null;
    }
}
