package com.me.security.feign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.Setter;

public class DefaultFeignInterceptor implements RequestInterceptor {

    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";

    @Setter
    private String authorizationKey;

    @Setter
    private String authorizationHeaderName = AUTHORIZATION_HEADER_NAME;

    @Override
    public void apply(RequestTemplate template) {
        template.header(authorizationHeaderName, authorizationKey);
    }
}
