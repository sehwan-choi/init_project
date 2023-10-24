package com.me.security.security.model;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class ClientAuthenticationToken extends AbstractAuthenticationToken {

    @Getter
    private final ClientAuthInfo authInfo;

    @Getter
    private final String accessToken;

    public ClientAuthenticationToken(ClientAuthInfo authInfo, String accessToken) {
        super(authInfo.getRoles());
        this.authInfo = authInfo;
        this.accessToken = accessToken;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.accessToken;
    }

    @Override
    public Object getPrincipal() {
        return this.authInfo;
    }
}
