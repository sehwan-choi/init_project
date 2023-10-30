package com.me.security.security.filter;

import com.me.security.security.model.AttemptAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private static final String TOKEN_AUTHORIZATION_HEADER_NAME = "Authorization";

    @Setter
    private String tokenAuthorizationHeaderName = TOKEN_AUTHORIZATION_HEADER_NAME;

    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Optional<String> optionalToken = obtainToken(request);
        if (optionalToken.isPresent()) {
            attemptAuthentication(optionalToken.get());
        } else {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    private void attemptAuthentication(String optionalToken) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new AttemptAuthenticationToken(optionalToken));
            SecurityContextHolder.setContext(new SecurityContextImpl(authenticate));
        } catch (Exception e) {
            log.warn("TokenAuthenticationFilter Exception", e);
            SecurityContextHolder.clearContext();
        }
    }

    private Optional<String> obtainToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(tokenAuthorizationHeaderName));
    }
}
