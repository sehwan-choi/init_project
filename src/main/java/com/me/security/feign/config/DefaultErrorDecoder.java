package com.me.security.feign.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.me.security.common.dto.ErrorResponse;
import com.me.security.feign.exception.FeignClientBadRequestException;
import com.me.security.feign.exception.FeignClientInternalServerException;
import com.me.security.feign.exception.FeignClientNotFoundException;
import com.me.security.feign.exception.FeignClientUnknownException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.Reader;
import java.nio.charset.Charset;

@Slf4j
@RequiredArgsConstructor
public class DefaultErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    @Override
    public Exception decode(String methodKey, Response response) {
        log.warn("Feign Error status : {}", response.status());

        ErrorResponse errorResponse;
        try {
            Reader reader = response.body().asReader(Charset.defaultCharset());
            errorResponse = objectMapper.readValue(reader, ErrorResponse.class);
        }catch (Exception e) {
            return e;
        }

        if (response.status() >= 400 && response.status() < 500) {
            if (response.status() == HttpStatus.NOT_FOUND.value()) {
                return new FeignClientNotFoundException(errorResponse);
            } else if (response.status() == HttpStatus.BAD_REQUEST.value()) {
                return new FeignClientBadRequestException(errorResponse);
            }
            return new FeignClientBadRequestException(errorResponse);
        } else if (response.status() >= 500 && response.status() < 600) {
            return new FeignClientInternalServerException(errorResponse);
        }
        return new FeignClientUnknownException(errorResponse);
    }
}
