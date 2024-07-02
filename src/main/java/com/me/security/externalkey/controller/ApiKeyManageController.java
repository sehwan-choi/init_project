package com.me.security.externalkey.controller;

import com.me.security.common.code.ServerCode;
import com.me.security.common.model.ApiResultResponse;
import com.me.security.common.swagger.annotation.SwaggerApiExampleValues;
import com.me.security.externalkey.dto.KeyRegistrationRequest;
import com.me.security.externalkey.dto.KeyRegistrationResponse;
import com.me.security.externalkey.service.KeyManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/key")
@RequiredArgsConstructor
@Tag(name = "해당 서비스를 이용하기 위한 API KEY관련 API", description = "해당 서버를 이용하기 위한 API KEY관련 API")
public class ApiKeyManageController {

    private final KeyManagementService managementService;

    @PostMapping
    @SwaggerApiExampleValues({
            ServerCode.SUCCESS,
            ServerCode.BAD_REQUEST
    })
    @Operation(summary = "API KEY 발급 API", description = "해당 서비스를 이용하도록 API KEY 를 발급한다.")
    public ApiResultResponse<KeyRegistrationResponse> keyAdd(@Valid @RequestBody KeyRegistrationRequest request) {
        return ApiResultResponse.success(managementService.keyRegistration(request));
    }

    @DeleteMapping("/{apiKey}")
    @SwaggerApiExampleValues({
            ServerCode.SUCCESS,
            ServerCode.BAD_REQUEST,
            ServerCode.API_KEY_NOT_FOUND,
            ServerCode.UNAUTHORIZED,
            ServerCode.INTERNAL_SERVER_ERROR
    })
    @Operation(summary = "API KEY Block", description = "기존에 발급받은 API KEY 를 block 시킨다")
    public ApiResultResponse<String> blockedKey(@Schema(description = "발급 받은 API Key") @PathVariable("apiKey") String apiKey) {
        managementService.keyDelete(apiKey);
        return ApiResultResponse.success();
    }
}
