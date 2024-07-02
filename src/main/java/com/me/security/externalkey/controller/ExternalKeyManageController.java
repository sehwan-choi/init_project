package com.me.security.externalkey.controller;

import com.me.security.common.code.ServerCode;
import com.me.security.common.model.ApiResultResponse;
import com.me.security.common.swagger.annotation.SwaggerApiExampleValues;
import com.me.security.externalkey.domain.ExternalKey;
import com.me.security.externalkey.dto.ExternalKeyCheckResponse;
import com.me.security.externalkey.service.KeyQueryService;
import com.me.security.security.model.ClientAuthenticationToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/external/api/v1/key")
@RequiredArgsConstructor
@Tag(name = "해당 서비스를 이용하기 위해 발급한 API KEY 관련 API", description = "해당 서비스를 이용하기 위해 발급한 API KEY 관련 API")
public class ExternalKeyManageController {

    private final KeyQueryService queryService;

    @GetMapping
    @SwaggerApiExampleValues({
            ServerCode.SUCCESS,
            ServerCode.BAD_REQUEST,
            ServerCode.API_KEY_NOT_FOUND,
            ServerCode.UNAUTHORIZED,
            ServerCode.INTERNAL_SERVER_ERROR
    })
    @Operation(summary = "발급받은 API KEY 정보 확인", description = "발급받은 API KEY 정보 확인한다.")
    public ApiResultResponse<ExternalKeyCheckResponse> check() {
        ClientAuthenticationToken authentication = (ClientAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        ExternalKey externalKey = queryService.findByKeyIfNoOptional(authentication.getAccessToken());
        return ApiResultResponse.success(new ExternalKeyCheckResponse(externalKey));
    }
}
