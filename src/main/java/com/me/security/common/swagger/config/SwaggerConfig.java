package com.me.security.common.swagger.config;

import com.me.security.common.code.ServerCode;
import com.me.security.common.model.ApiResultResponse;
import com.me.security.common.swagger.annotation.SwaggerApiExampleValue;
import com.me.security.common.swagger.annotation.SwaggerApiExampleValues;
import com.me.security.common.swagger.model.ExampleHolder;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.method.HandlerMethod;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;


@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    private final MessageSource messageSource;

    private static final String BEARER_TOKEN_PREFIX = "Bearer";

    @Bean
    @Profile("!Prod")
    public OpenAPI openAPI() {
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(BEARER_TOKEN_PREFIX);
        Components components = new Components()
                .addSecuritySchemes(BEARER_TOKEN_PREFIX, new SecurityScheme()
                        .name(BEARER_TOKEN_PREFIX)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme(BEARER_TOKEN_PREFIX)
                        .bearerFormat(BEARER_TOKEN_PREFIX));

        return new OpenAPI()
                .addSecurityItem(securityRequirement)
                .components(components);
    }

    @Bean
    public OperationCustomizer customize() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            SwaggerApiExampleValues apiErrorCodeExamples = handlerMethod.getMethodAnnotation(
                    SwaggerApiExampleValues.class);

            // @SwaggerApiErrorCodes 어노테이션이 붙어있다면
            if (apiErrorCodeExamples != null) {
                generateErrorCodeResponseExample(operation, apiErrorCodeExamples.value());
            } else {
                SwaggerApiExampleValue apiErrorCodeExample = handlerMethod.getMethodAnnotation(
                        SwaggerApiExampleValue.class);

                // @SwaggerApiErrorCodes 어노테이션이 붙어있지 않고
                // @SwaggerApiErrorCode 어노테이션이 붙어있다면
                if (apiErrorCodeExample != null) {
                    generateErrorCodeResponseExample(operation, apiErrorCodeExample.value());
                }
            }

            return operation;
        };
    }

    // 여러 개의 에러 응답값 추가
    private void generateErrorCodeResponseExample(Operation operation, ServerCode[] errorCodes) {
        ApiResponses responses = operation.getResponses();

        // ExampleHolder(에러 응답값) 객체를 만들고 에러 코드별로 그룹화
        Map<Integer, List<ExampleHolder>> statusWithExampleHolders = Arrays.stream(errorCodes)
                .map(
                        errorCode -> ExampleHolder.builder()
                                .holder(getSwaggerExample(errorCode))
                                .code(errorCode.getHttpStatus().value())
                                .name(errorCode.name())
                                .build()
                )
                .collect(Collectors.groupingBy(ExampleHolder::getCode));

        // ExampleHolders를 ApiResponses에 추가
        addExamplesToResponses(responses, statusWithExampleHolders);
    }

    // 단일 에러 응답값 예시 추가
    private void generateErrorCodeResponseExample(Operation operation, ServerCode errorCode) {
        ApiResponses responses = operation.getResponses();

        // ExampleHolder 객체 생성 및 ApiResponses에 추가
        ExampleHolder exampleHolder = ExampleHolder.builder()
                .holder(getSwaggerExample(errorCode))
                .name(errorCode.name())
                .code(errorCode.getHttpStatus().value())
                .build();
        addExamplesToResponses(responses, exampleHolder);
    }

    // SwaggerErrorResponse 형태의 예시 객체 생성
    private Example getSwaggerExample(ServerCode errorCode) {
        String message = ServerCode.SUCCESS.equals(errorCode) ? "SUCCESS" : messageSource.getMessage(errorCode.getMessageCode(), null, Locale.getDefault());
        ApiResultResponse<Object> response = ApiResultResponse.ofResponse(errorCode.getCode(), message, new Object());

        Example example = new Example();
        example.setValue(response);

        return example;
    }

    // exampleHolder를 ApiResponses에 추가
    private void addExamplesToResponses(ApiResponses responses,
                                        Map<Integer, List<ExampleHolder>> statusWithExampleHolders) {
        statusWithExampleHolders.forEach(
                (status, v) -> {
                    Content content = new Content();
                    MediaType mediaType = new MediaType();
                    ApiResponse apiResponse = new ApiResponse();

                    v.forEach(
                            exampleHolder -> mediaType.addExamples(
                                    exampleHolder.getName(),
                                    exampleHolder.getHolder()
                            )
                    );
                    content.addMediaType("application/json", mediaType);
                    apiResponse.setContent(content);
                    responses.addApiResponse(String.valueOf(status), apiResponse);
                }
        );
    }

    private void addExamplesToResponses(ApiResponses responses, ExampleHolder exampleHolder) {
        Content content = new Content();
        MediaType mediaType = new MediaType();
        ApiResponse apiResponse = new ApiResponse();

        mediaType.addExamples(exampleHolder.getName(), exampleHolder.getHolder());
        content.addMediaType("application/json", mediaType);
        apiResponse.content(content);
        responses.addApiResponse(String.valueOf(exampleHolder.getCode()), apiResponse);
    }
}
