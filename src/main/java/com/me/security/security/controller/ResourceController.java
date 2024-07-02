package com.me.security.security.controller;

import com.me.security.common.code.ServerCode;
import com.me.security.common.model.ApiResultResponse;
import com.me.security.common.swagger.annotation.SwaggerApiExampleValues;
import com.me.security.security.service.ExternalKeyStorage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/resource")
@RestController
@RequiredArgsConstructor
@Tag(name = "API Key InMemory 등록 관련 API", description = "API Key InMemory 등록 관련 API")
public class ResourceController {

    private final ExternalKeyStorage storage;

    @PostMapping("/reload")
    @SwaggerApiExampleValues({
            ServerCode.SUCCESS,
            ServerCode.USER_NOT_FOUND
    })
    @Operation(summary = "API Key InMemory 재설정", description = "API Key InMemory 재설정 API")
    public ApiResultResponse<String> reload() {
        storage.reload();
        return ApiResultResponse.success();
    }
}
