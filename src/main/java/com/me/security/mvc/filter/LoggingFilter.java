package com.me.security.mvc.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.UUID;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        try {
            MDC.put("request_id", UUID.randomUUID().toString());

            log.debug("URI : " + request.getRequestURI());
            log.debug("Method : " + request.getMethod());
            printHeaders(request);
            printQueryParameters(request);
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