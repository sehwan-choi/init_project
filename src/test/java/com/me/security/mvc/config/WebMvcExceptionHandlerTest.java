package com.me.security.mvc.config;

import com.me.security.common.code.ServerCode;
import com.me.security.common.exception.InternalServerException;
import com.me.security.common.exception.InvalidDataException;
import com.me.security.common.model.ApiResultResponse;
import com.me.security.feign.exception.DefaultClientException;
import jakarta.servlet.http.HttpServletRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

        private final String error_invalid_data = "잘못된 요청입니다.";
        private final String error_resource_not_found = "정보를 찾을 수 없습니다.";
        private final String error_internal_server = "일시적인 오류가 발생하였습니다.\n잠시 후 다시 시도해주세요.";

        @Test
        @DisplayName("400 에러 처리")
        void error_400() {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            Exception ex = new Exception();
            ServerCode error = ServerCode.BAD_REQUEST;
            when(messageSource.getMessage(error.getMessageCode(), null , webRequest.getLocale())).thenReturn(error_invalid_data);

            ResponseEntity<Object> response = handler.handleExceptionInternal(ex, BODY, headers, status, webRequest);
            ResponseEntity<ApiResultResponse> expect = ResponseEntity.badRequest()
                    .headers(headers)
                    .body(ApiResultResponse.ofResponse(error.getCode(), error_invalid_data));

            responseBodyCheck(response, expect);
        }

        @Test
        @DisplayName("404 에러 처리")
        void error_404() {
            HttpStatus status = HttpStatus.NOT_FOUND;
            Exception ex = new Exception();
            ServerCode error = ServerCode.NOT_FOUND;
            when(messageSource.getMessage(error.getMessageCode(), null , webRequest.getLocale())).thenReturn(error_resource_not_found);

            ResponseEntity<Object> response = handler.handleExceptionInternal(ex, BODY, headers, status, webRequest);
            ResponseEntity<ApiResultResponse> expect = ResponseEntity.badRequest()
                    .headers(headers)
                    .body(ApiResultResponse.ofResponse(error.getCode(), error_resource_not_found));

            responseBodyCheck(response, expect);
        }

        @Test
        @DisplayName("500 에러 처리")
        void error_500() {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            Exception ex = new Exception();
            ServerCode error = ServerCode.INTERNAL_SERVER_ERROR;
            when(messageSource.getMessage(error.getMessageCode(), null , webRequest.getLocale())).thenReturn(error_internal_server);

            ResponseEntity<Object> response = handler.handleExceptionInternal(ex, BODY, headers, status, webRequest);
            ResponseEntity<ApiResultResponse> expect = ResponseEntity.badRequest()
                    .headers(headers)
                    .body(ApiResultResponse.ofResponse(error.getCode(), error_internal_server));

            responseBodyCheck(response, expect);
        }
    }

    @Nested
    @DisplayName("어플리케이션 Exception Handler")
    class applicationExceptionHandle {

        private final String error_invalid_data = "잘못된 요청입니다.";
        private final String error_resource_not_found = "정보를 찾을 수 없습니다.";
        private final String error_internal_server = "일시적인 오류가 발생하였습니다.\n잠시 후 다시 시도해주세요.";
        private final String error_denied="접근할 수 없습니다.";

        @Test
        @DisplayName("Application 400 에러 처리 - 잘못된 요청")
        void error_400_invalid_request() {
            InvalidDataException ex = new InvalidDataException();
            ServerCode error = ServerCode.BAD_REQUEST;
            when(messageSource.getMessage(error.getMessageCode(), null , webRequest.getLocale())).thenReturn(error_invalid_data);

            ResponseEntity<Object> response = handler.handleApplicationException(ex, webRequest);
            ResponseEntity<ApiResultResponse> expect = ResponseEntity.badRequest()
                    .headers(headers)
                    .body(ApiResultResponse.ofResponse(error.getCode(), error_invalid_data));
            responseBodyCheck(response, expect);
        }

        @Test
        @DisplayName("FeignClient Exception - Client Error 400 에러 처리")
        void feignClient_error_400() {
            DefaultClientException ex = new DefaultClientException(ServerCode.BAD_REQUEST, "error!");
            ServerCode error = ServerCode.BAD_REQUEST;
            when(messageSource.getMessage(error.getMessageCode(), null , webRequest.getLocale())).thenReturn(error_invalid_data);

            ResponseEntity<Object> response = handler.handleApplicationException(ex, webRequest);
            ResponseEntity<ApiResultResponse> expect = ResponseEntity.badRequest()
                    .headers(headers)
                    .body(ApiResultResponse.ofResponse(error.getCode(), error_invalid_data));

            responseBodyCheck(response, expect);
        }

        @Test
        @DisplayName("FeignClient Exception - Client Error 500 에러 처리")
        void feignClient_error_500() {
            DefaultClientException ex = new DefaultClientException(ServerCode.INTERNAL_SERVER_ERROR, "error!");
            ServerCode error = ServerCode.INTERNAL_SERVER_ERROR;
            when(messageSource.getMessage(error.getMessageCode(), null , webRequest.getLocale())).thenReturn(error_internal_server);

            ResponseEntity<Object> response = handler.handleApplicationException(ex, webRequest);
            ResponseEntity<ApiResultResponse> expect = ResponseEntity.badRequest()
                    .headers(headers)
                    .body(ApiResultResponse.ofResponse(error.getCode(), error_internal_server));

            responseBodyCheck(response, expect);
        }

        @Test
        @DisplayName("Application 500 에러 처리")
        void error_500() {
            InternalServerException ex = new InternalServerException();
            ServerCode error = ServerCode.INTERNAL_SERVER_ERROR;
            when(messageSource.getMessage(error.getMessageCode(), null , webRequest.getLocale())).thenReturn(error_internal_server);

            ResponseEntity<Object> response = handler.handleApplicationException(ex, webRequest);
            ResponseEntity<ApiResultResponse> expect = ResponseEntity.badRequest()
                    .headers(headers)
                    .body(ApiResultResponse.ofResponse(error.getCode(), error_internal_server));

            responseBodyCheck(response, expect);
        }
    }

    void responseBodyCheck(ResponseEntity<Object> actual, ResponseEntity<ApiResultResponse> expect) {

        Assertions.assertThat(((ApiResultResponse)actual.getBody()).getMessage()).isEqualTo(expect.getBody().getMessage());

        Assertions.assertThat(((ApiResultResponse)actual.getBody()).getCode()).isEqualTo(expect.getBody().getCode());
    }
}