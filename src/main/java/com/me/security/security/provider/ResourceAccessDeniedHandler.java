package com.me.security.security.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.me.security.common.code.ServerCode;
import com.me.security.common.dto.ErrorResponse;
import com.me.security.common.model.ApiResultResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;

public class ResourceAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    private final MessageSource messageSource;

    public ResourceAccessDeniedHandler(ObjectMapper objectMapper, MessageSource messageSource) {
        this.objectMapper = objectMapper;
        this.messageSource = messageSource;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String message = messageSource.getMessage("error.forbidden", null, LocaleContextHolder.getLocale());

        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());

        try (OutputStream outputStream = response.getOutputStream()) {
            objectMapper.writeValue(outputStream, ApiResultResponse.ofResponse(ServerCode.FORBIDDEN.getCode(), messageSource.getMessage(ServerCode.FORBIDDEN.getMessageCode(), null, request.getLocale())));
            outputStream.flush();
        }
    }
}
