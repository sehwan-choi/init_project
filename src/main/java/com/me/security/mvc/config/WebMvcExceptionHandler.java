package com.me.security.mvc.config;

import com.me.security.common.dto.ErrorResponse;
import com.me.security.common.exception.ExceptionCommonCode;
import com.me.security.common.exception.InvalidDataException;
import com.me.security.common.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

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
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        if (ex instanceof ResourceNotFoundException) {
            ResourceNotFoundException notFoundException = ((ResourceNotFoundException) ex);
            String message = messageSource.getMessage(notFoundException.getCode(), notFoundException.getArgs(), request.getLocale());
            return new ResponseEntity<>(new ErrorResponse(LocalDateTime.now(), message, path), HttpStatus.NOT_FOUND);
        } else if (ex instanceof InvalidDataException) {
            InvalidDataException invalidDataException = ((InvalidDataException) ex);
            String message = messageSource.getMessage(invalidDataException.getCode(), invalidDataException.getArgs(), request.getLocale());
            return new ResponseEntity<>(new ErrorResponse(LocalDateTime.now(), message, path), HttpStatus.BAD_REQUEST);
        }
//        else if (ex instanceof TokenException) {
//            String message = messageSource.getMessage(ExceptionCommonCode.UNAUTHORIZED_ERROR, null, request.getLocale());
//            Long resultCode = ((TokenException) ex).getResultCode();
//            if (resultCode != null) {
//                return new ResponseEntity<>(new ErrorResponse(LocalDateTime.now(), message, path, resultCode), HttpStatus.UNAUTHORIZED);
//            }
//            return new ResponseEntity<>(new ErrorResponse(LocalDateTime.now(), message, path), HttpStatus.UNAUTHORIZED);
//        }

        String message = messageSource.getMessage(ExceptionCommonCode.INTERNAL_SERVER_ERROR, null, request.getLocale());
        return new ResponseEntity<>(new ErrorResponse(LocalDateTime.now(), message, path), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        log.error("handleExceptionInternal::" , ex);
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
         if (status.equals(HttpStatus.BAD_REQUEST)) {
            if (ex instanceof MethodArgumentNotValidException) {
                MethodArgumentNotValidException notValidException = (MethodArgumentNotValidException) ex;
                String error = notValidException.getBindingResult().getFieldErrors().stream().map(m -> m.getField() + " " + m.getDefaultMessage()).collect(Collectors.joining("\n"));
                return new ResponseEntity<>(new ErrorResponse(LocalDateTime.now(), error, path), headers, HttpStatus.BAD_REQUEST);
            } else if (ex instanceof HttpMessageNotReadableException) {
                HttpMessageNotReadableException notReadableException = (HttpMessageNotReadableException) ex;
                String message = getLastMessage(notReadableException);
                return new ResponseEntity<>(new ErrorResponse(LocalDateTime.now(), message, path), headers, HttpStatus.BAD_REQUEST);
            }
            String message = messageSource.getMessage(ExceptionCommonCode.BAD_REQUEST_ERROR, null, request.getLocale());
            return new ResponseEntity<>(new ErrorResponse(LocalDateTime.now(), message,path), headers, HttpStatus.BAD_REQUEST);
        } else if(status.equals(HttpStatus.NOT_FOUND)) {
            String message = messageSource.getMessage(ExceptionCommonCode.RESOURCE_NOT_FOUND_ERROR, null, request.getLocale());
            return new ResponseEntity<>(new ErrorResponse(LocalDateTime.now(), message,path), headers, HttpStatus.NOT_FOUND);
        } else if(status.equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
            String message = messageSource.getMessage(ExceptionCommonCode.INTERNAL_SERVER_ERROR, null, request.getLocale());
            return new ResponseEntity<>(new ErrorResponse(LocalDateTime.now(), message,path), headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        ResponseEntity<Object> objectResponseEntity = super.handleExceptionInternal(ex, body, headers, status, request);
        return convertResponseEntity(objectResponseEntity, headers, status, request);
    }

    private String getLastMessage(Exception e) {
        Throwable lastCause = getLastCause(e);
        return lastCause.getMessage().split("\n")[0];
    }

    private Throwable getLastCause(Exception e) {
        Throwable th = e.getCause();
        while (true) {
            if (th.getCause() == null) {
                return th;
            }
            th = th.getCause();
        }
    }

    private ResponseEntity<Object> convertResponseEntity(ResponseEntity<Object> objectResponseEntity, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        if (checkEntityBody(objectResponseEntity)) {
            ProblemDetail problemDetail = (ProblemDetail) objectResponseEntity.getBody();

            ServletWebRequest servletWebRequest = (ServletWebRequest) request;
            ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), getMessage(problemDetail), servletWebRequest.getRequest().getRequestURI());
            return new ResponseEntity<>(errorResponse, headers, status);
        }
        return objectResponseEntity;
    }

    private boolean checkEntityBody(ResponseEntity<Object> objectResponseEntity) {
        if (objectResponseEntity == null) {
            return false;
        }

        if (objectResponseEntity.getBody() == null) {
            return false;
        }

        if (objectResponseEntity.getBody() instanceof ErrorResponse) {
            return false;
        }

        return objectResponseEntity.getBody() instanceof ProblemDetail;
    }

    private String getMessage(ProblemDetail problemDetail) {
        StringBuilder sb = new StringBuilder();
        if (problemDetail != null) {
            if (problemDetail.getTitle() != null) {
                sb.append(problemDetail.getTitle()).append(". ");
            }
            sb.append(problemDetail.getDetail());
        }
        return sb.toString();
    }
}
