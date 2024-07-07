package com.me.security.mvc.config;

import com.me.security.common.code.ServerCode;
import com.me.security.common.exception.InternalServerException;
import com.me.security.common.exception.InvalidDataException;
import com.me.security.common.exception.ResourceNotFoundException;
import com.me.security.common.model.ApiResultResponse;
import com.me.security.feign.exception.DefaultClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class WebMvcExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler({
            ResourceNotFoundException.class,
            InvalidDataException.class,
            Exception.class
    })
    public ResponseEntity<Object> handleApplicationException(Exception ex, WebRequest request) {

        log.error("handleApplicationException::" , ex);

        ServerCode code = null;
        HttpStatus httpStatus = null;

        if (ex instanceof DefaultClientException clientEx) {
            httpStatus = clientEx.getCode().equals(ServerCode.BAD_REQUEST) ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR;
            code = clientEx.getCode();
        } else if (ex instanceof InvalidDataException invalidEx) {
            httpStatus = HttpStatus.BAD_REQUEST;
            code = invalidEx.getCode();
        } else if (ex instanceof InternalServerException internalEx) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            code = internalEx.getCode();
        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            code = ServerCode.INTERNAL_SERVER_ERROR;
        }
        String message = messageSource.getMessage(code.getMessageCode(), null, request.getLocale());
        return new ResponseEntity<>(ApiResultResponse.ofResponse(code.getCode(), message), httpStatus);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        log.error("handleExceptionInternal::" , ex);

        ServerCode code = null;
        HttpStatus httpStatus = null;

        if (status.equals(HttpStatus.NOT_FOUND)) {
            code = ServerCode.NOT_FOUND;
            httpStatus = HttpStatus.NOT_FOUND;
        } else if (status.equals(HttpStatus.BAD_REQUEST)) {
            code = ServerCode.BAD_REQUEST;
            httpStatus = HttpStatus.BAD_REQUEST;
        } else {
            code = ServerCode.INTERNAL_SERVER_ERROR;
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        String message = messageSource.getMessage(code.getMessageCode(), null, request.getLocale());
        return new ResponseEntity<>(ApiResultResponse.ofResponse(code.getCode(), message), httpStatus);
    }
}
