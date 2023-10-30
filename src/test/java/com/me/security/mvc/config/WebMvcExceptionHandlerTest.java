package com.me.security.mvc.config;

import com.me.security.common.dto.ErrorResponse;
import com.me.security.common.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.context.request.ServletWebRequest;

import java.time.Clock;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WebMvcExceptionHandlerTest {

    private final MessageSource messageSource = mock(MessageSource.class);
    WebMvcExceptionHandler handler = new WebMvcExceptionHandler(messageSource);

    private static final String PATH = "/path";

    private static final String BODY = "BODY";

    ServletWebRequest webRequest;
    HttpHeaders headers;

    @BeforeEach
    void beforeEach() {
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        webRequest = new ServletWebRequest(httpServletRequest);
        headers = new HttpHeaders();

        when(httpServletRequest.getRequestURI()).thenReturn(PATH);
    }

    @Nested
    @DisplayName("Spring MVC Exception Handler")
    class mvcExceptionHandle {
        private final String error_authorized= "인증정보가 변경되었습니다.";
        private final String error_forbidden="접근할 수 없습니다.";
        private final String error_resource_not_found="요청 정보를 찾을 수 없습니다.";
        private final String error_internal_server="일시적인 오류가 발생하였습니다.\n잠시후 다시 시도해 주세요.";
        private final String error_invalid_data="요청을 처리할 수 없습니다.";
        private final String error_denied="요청이 거절 되었습니다.";

        private final String unsupported_media_type = "Unsupported Media Type. Content-Type 'null' is not supported.";

        @Test
        @DisplayName("400 에러 처리")
        void error_400() {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            Exception ex = new Exception();
            when(messageSource.getMessage(ExceptionCommonCode.BAD_REQUEST_ERROR, null , webRequest.getLocale())).thenReturn(error_invalid_data);

            ResponseEntity<Object> response = handler.handleExceptionInternal(ex, BODY, headers, status, webRequest);
            ResponseEntity<ErrorResponse> expect = ResponseEntity.badRequest()
                    .headers(headers)
                    .body(new ErrorResponse(LocalDateTime.now(), error_invalid_data, PATH));

            responseBodyCheck(response, expect);
        }

        @Test
        @DisplayName("404 에러 처리")
        void error_404() {
            HttpStatus status = HttpStatus.NOT_FOUND;
            Exception ex = new Exception();
            when(messageSource.getMessage(ExceptionCommonCode.RESOURCE_NOT_FOUND_ERROR, null , webRequest.getLocale())).thenReturn(error_resource_not_found);

            ResponseEntity<Object> response = handler.handleExceptionInternal(ex, BODY, headers, status, webRequest);
            ResponseEntity<ErrorResponse> expect = ResponseEntity.badRequest()
                    .headers(headers)
                    .body(new ErrorResponse(LocalDateTime.now(), error_resource_not_found, PATH));

            responseBodyCheck(response, expect);
        }

        @Test
        @DisplayName("500 에러 처리")
        void error_500() {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            Exception ex = new Exception();
            when(messageSource.getMessage(ExceptionCommonCode.INTERNAL_SERVER_ERROR, null , webRequest.getLocale())).thenReturn(error_internal_server);

            ResponseEntity<Object> response = handler.handleExceptionInternal(ex, BODY, headers, status, webRequest);
            ResponseEntity<ErrorResponse> expect = ResponseEntity.badRequest()
                    .headers(headers)
                    .body(new ErrorResponse(LocalDateTime.now(), error_internal_server, PATH));

            responseBodyCheck(response, expect);
        }
    }

    @Nested
    @DisplayName("어플리케이션 Exception Handler")
    class applicationExceptionHandle {

        private final String error_authorized="인증정보가 변경되었습니다.";
        private final String error_forbidden="접근할 수 없습니다.";
        private final String error_resource_not_found="요청 정보를 찾을 수 없습니다.";
        private final String error_internal_server="일시적인 오류가 발생하였습니다.\n잠시후 다시 시도해 주세요.";
        private final String error_invalid_data="요청을 처리할 수 없습니다.";
        private final String error_denied="요청이 거절 되었습니다.";

        @Test
        @DisplayName("Application 400 에러 처리 - 잘못된 요청")
        void error_400_invalid_request() {
            InvalidDataException ex = new InvalidDataException();
            when(messageSource.getMessage(ExceptionCommonCode.BAD_REQUEST_ERROR, ex.getArgs() , webRequest.getLocale())).thenReturn(error_invalid_data);

            ResponseEntity<Object> response = handler.handleApplicationException(ex, webRequest);
            ResponseEntity<ErrorResponse> expect = ResponseEntity.badRequest()
                    .headers(headers)
                    .body(new ErrorResponse(LocalDateTime.now(), error_invalid_data, PATH));
            responseBodyCheck(response, expect);
        }

        @Test
        @DisplayName("Application 400 에러 처리 - access Deined")
        void error_400_access_denied() {
            AccessDeniedException ex = new AccessDeniedException();
            when(messageSource.getMessage(ExceptionCommonCode.ACCESS_DENIED_ERROR, ex.getArgs() , webRequest.getLocale())).thenReturn(error_denied);

            ResponseEntity<Object> response = handler.handleApplicationException(ex, webRequest);
            ResponseEntity<ErrorResponse> expect = ResponseEntity.badRequest()
                    .headers(headers)
                    .body(new ErrorResponse(LocalDateTime.now(), error_denied, PATH));
            responseBodyCheck(response, expect);
        }

        @Test
        @DisplayName("Application 404 에러 처리")
        void error_404() {
            ResourceNotFoundException ex = new ResourceNotFoundException();
            when(messageSource.getMessage(ExceptionCommonCode.RESOURCE_NOT_FOUND_ERROR, ex.getArgs() , webRequest.getLocale())).thenReturn(error_resource_not_found);

            ResponseEntity<Object> response = handler.handleApplicationException(ex, webRequest);
            ResponseEntity<ErrorResponse> expect = ResponseEntity.badRequest()
                    .headers(headers)
                    .body(new ErrorResponse(LocalDateTime.now(), error_resource_not_found, PATH));

            responseBodyCheck(response, expect);
        }

        @Test
        @DisplayName("Application 500 에러 처리")
        void error_500() {
            InternalServerException ex = new InternalServerException();
            when(messageSource.getMessage(ExceptionCommonCode.INTERNAL_SERVER_ERROR, null , webRequest.getLocale())).thenReturn(error_internal_server);

            ResponseEntity<Object> response = handler.handleApplicationException(ex, webRequest);
            ResponseEntity<ErrorResponse> expect = ResponseEntity.badRequest()
                    .headers(headers)
                    .body(new ErrorResponse(LocalDateTime.now(), error_internal_server, PATH));

            responseBodyCheck(response, expect);
        }
    }

    void responseBodyCheck(ResponseEntity<Object> actual, ResponseEntity<ErrorResponse> expect) {

        Assertions.assertThat(((ErrorResponse)actual.getBody()).getMessage()).isEqualTo(expect.getBody().getMessage());

        Assertions.assertThat(((ErrorResponse)actual.getBody()).getPath()).isEqualTo(expect.getBody().getPath());
        Assertions.assertThat(((ErrorResponse)actual.getBody()).getCode()).isEqualTo(expect.getBody().getCode());
    }
}