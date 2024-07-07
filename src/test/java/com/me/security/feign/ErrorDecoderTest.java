//package com.me.security.feign;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.me.security.common.dto.ErrorResponse;
//import com.me.security.feign.config.DefaultClientErrorDecoder;
//import feign.Response;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//import java.io.Reader;
//import java.nio.charset.Charset;
//import java.time.LocalDateTime;
//
//import static org.mockito.Mockito.*;
//
//public class ErrorDecoderTest {
//
//    ObjectMapper objectMapper = mock(ObjectMapper.class);
//
//    DefaultClientErrorDecoder decoder = new DefaultClientErrorDecoder();
//
//    Response response = mock(Response.class);
//    Response.Body body = mock(Response.Body.class);
//    Reader reader = mock(Reader.class);
//
//    ErrorResponse errorResponse = createErrorResponse();
//
//    public ErrorResponse createErrorResponse() {
//        return new ErrorResponse(LocalDateTime.now(), "message", "path");
//    }
//
//    @BeforeEach
//    void beforeEach() throws IOException {
//        when(response.body()).thenReturn(body);
//        when(body.asReader(Charset.defaultCharset())).thenReturn(reader);
//    }
//
//    @Test
//    void _400Error() {
//        when(response.status()).thenReturn(400);
//        Exception exception = decoder.decode("any", response);
//        Assertions.assertThat(exception).isInstanceOf(FeignClientBadRequestException.class);
//    }
//    @Test
//    void _404Error() {
//        when(response.status()).thenReturn(404);
//        Exception exception = decoder.decode("any", response);
//        Assertions.assertThat(exception).isInstanceOf(FeignClientNotFoundException.class);
//    }
//
//    @Test
//    void _4XXError() {
//        when(response.status()).thenReturn(401);
//        Exception exception = decoder.decode("any", response);
//        Assertions.assertThat(exception).isInstanceOf(FeignClientBadRequestException.class);
//    }
//
//    @Test
//    void _5XXError() {
//        when(response.status()).thenReturn(500);
//        Exception exception = decoder.decode("any", response);
//        Assertions.assertThat(exception).isInstanceOf(FeignClientInternalServerException.class);
//    }
//
//    @Test
//    void unknownError() {
//        when(response.status()).thenReturn(1);
//        Exception exception = decoder.decode("any", response);
//        Assertions.assertThat(exception).isInstanceOf(FeignClientUnknownException.class);
//    }
//}
