package com.me.security.mvc.filter;

import com.me.security.common.generator.KeyGenerator;
import com.me.security.mvc.domain.MDCKey;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class LoggingFilter extends OncePerRequestFilter {

    private final List<AntPathRequestMatcher> excludePath = new ArrayList<>();

    private final KeyGenerator keyGenerator;

    @PostConstruct
    public void init() {
        excludePath.add(new AntPathRequestMatcher("/actuator*"));
        excludePath.add(new AntPathRequestMatcher("/actuator/*"));
        excludePath.add(new AntPathRequestMatcher("//h2-console/*"));
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return excludePath.stream().anyMatch(f -> f.matches(request));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        try {
            MDC.put(MDCKey.TRX_ID.getKey(), keyGenerator.generate());

            log.debug("URI : " + requestWrapper.getRequestURI());
            log.debug("Method : " + requestWrapper.getMethod());
            printHeaders(requestWrapper);
            printQueryParameters(requestWrapper);
            printContentBody(requestWrapper.getContentAsByteArray(), "Request Body");

            filterChain.doFilter(requestWrapper, responseWrapper);

            printContentBody(responseWrapper.getContentAsByteArray(), "Response Body");

            responseWrapper.copyBodyToResponse();
        } finally {
            MDC.clear();
        }
    }

    private void printHeaders(HttpServletRequest request) {
        Enumeration<String> headerArray = request.getHeaderNames();
        while (headerArray.hasMoreElements()) {
            String headerName = headerArray.nextElement();
            log.debug("[Header] " + headerName + " : " + request.getHeader(headerName));
        }
    }

    private void printQueryParameters(HttpServletRequest request) {
        request.getParameterMap()
                .forEach((key, value) -> log.debug("[QueryParam] " + key + " : " +  String.join("", value)));
    }

    private void printContentBody(final byte[] contents, String prefix) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(contents)));
        bufferedReader.lines().forEach(str -> log.debug(prefixForm(prefix) + " " + str));
    }

    private String prefixForm(String prefix) {
        return "[" + prefix + "]";
    }
}