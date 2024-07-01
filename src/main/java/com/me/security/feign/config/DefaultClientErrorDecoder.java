package com.me.security.feign.config;

import com.me.security.common.code.ServerCode;
import com.me.security.feign.exception.DefaultClientException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
public class DefaultClientErrorDecoder implements ErrorDecoder {

    /**
     * response httpStatus 4XX 에러 인경우 HttpStatus : 400 을 발생시키는 Exception return(클라이언트 요청오류)
     * response httpStatus 4XX 에러 아닌경우 HttpStatus : 500 을 발생시키는 Exception return(서버 오류)
     */
    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            String message = null;
            byte[] bytes = null;
            if (response.body() != null) {
                bytes = Util.toByteArray(response.body().asInputStream());
                message = new String(bytes, StandardCharsets.UTF_8);
            } else {
                message = response.toString();
            }

            if (response.status() >= 400 && response.status() <= 499) {
                return new DefaultClientException(ServerCode.BAD_REQUEST, message);
            } else {
                return new DefaultClientException(ServerCode.INTERNAL_SERVER_ERROR, message);
            }
        } catch (Exception e) {
            return new DefaultClientException(ServerCode.INTERNAL_SERVER_ERROR, response.toString(), e);
        }
    }
}
