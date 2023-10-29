package com.me.security.security.provider;

import com.me.security.security.exception.BadTokenAuthorizationException;
import com.me.security.security.exception.ClientNotFoundAuthenticationException;
import com.me.security.security.exception.ClientStatusAuthenticationException;
import com.me.security.security.model.AttemptAuthenticationToken;
import com.me.security.security.model.ClientAuthInfo;
import com.me.security.security.model.ClientAuthenticationToken;
import com.me.security.security.model.VerifyResult;
import com.me.security.security.service.AccessAuthorizationService;
import com.me.security.security.service.AuthenticationTokenVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Optional;

@RequiredArgsConstructor
public class TokenAuthenticationProvider implements AuthenticationProvider {

    private final AuthenticationTokenVerifier verifyService;

    private final AccessAuthorizationService authorizationService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        AttemptAuthenticationToken authenticationToken = (AttemptAuthenticationToken) authentication;
        String accessToken = authenticationToken.getAccessToken();

        //User Token or Server Token 검증
        Optional<VerifyResult> verify = verifyService.verify(accessToken);
        if (verify.isEmpty()) {
            throw new BadTokenAuthorizationException("The token is invalid. token : \"" + accessToken + "\"");
        }

        Long clientId = verify.get().getId();
        try {
            ClientAuthInfo access = authorizationService.getAccessClient(clientId);
            return new ClientAuthenticationToken(access, accessToken);
        } catch (ClientNotFoundAuthenticationException e) {
            throw new BadCredentialsException("Bad Credential Authorization! clientId : \"" + clientId + "\" accessToken : \"" + accessToken + "\"", e);
        } catch (ClientStatusAuthenticationException e2) {
            throw new BadCredentialsException("Bad Credential Authorization Token Expired! clientId : \"" + clientId + "\" accessToken : \"" + accessToken + "\"", e2);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AttemptAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
