package com.me.security.feign.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class DefaultRequestHeaderInterceptor implements RequestInterceptor {

    private static final String DEFAULT_AUTHORIZATION_NAME = "Authorization";

    @Setter
    private String defaultAuthorizationName = DEFAULT_AUTHORIZATION_NAME;

    @Value("${feign.auth.key}")
    private String authKey;

    @Override
    public void apply(RequestTemplate template) {
        template.header(defaultAuthorizationName, authKey);
    }
}
