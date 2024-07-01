package com.me.security.feign.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
public class FeignConfiguration {

    /**
     * FeignClient encoder 설정
     * @return
     */
    @Bean
    public Encoder encoder() {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter(clientObjectMapper());
        messageConverter.setDefaultCharset(StandardCharsets.UTF_8);
        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(List.of(messageConverter));

        return new SpringEncoder(objectFactory);
    }

    /**
     * FeignClient decoder 설정
     */
    @Bean
    public Decoder decoder() {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter(clientObjectMapper());
        messageConverter.setDefaultCharset(StandardCharsets.UTF_8);
        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(List.of(messageConverter));

        return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new DefaultClientErrorDecoder();
    }

    @Bean
    public Logger.Level loggingLevel() {
        return Logger.Level.FULL;
    }


    @Bean
    public RequestInterceptor authorizeInterceptor() {
        return new DefaultRequestHeaderInterceptor();
    }


    /**
     * @RequestParam에 LocalDateTime, LocalDate, LocalTime 사용시
     * startDate=20.%209.%2030.&endDate=20.%2010.%201. 와같이 API 요청하게 되기때문에 아래와 같이 타임포맷을 설정한다.
     * @return
     */
    @Bean
    public  FeignFormatterRegistrar localDateFeignFormatterRegister() {
        return registry -> {
            DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
//            registrar.setUseIsoFormat(true);
            registrar.setDateFormatter(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            registrar.setTimeFormatter(DateTimeFormatter.ofPattern("HH:mm:ss"));
            registrar.setDateTimeFormatter(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            registrar.registerFormatters(registry);
        };
    }

    public ObjectMapper clientObjectMapper() {
        Module javaTimeModule = new JavaTimeModule()
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
//                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME))

                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
//                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME))

                .addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ISO_DATE))
                .addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ISO_DATE))

                .addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")))
//                .addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ISO_TIME))

                .addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
//                .addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ISO_TIME));

        ObjectMapper mapper = new ObjectMapper()
                .registerModule(javaTimeModule)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        return mapper;
    }
}
