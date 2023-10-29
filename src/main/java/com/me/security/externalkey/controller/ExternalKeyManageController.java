package com.me.security.externalkey.controller;

import com.me.security.common.dto.SimpleResponse;
import com.me.security.externalkey.domain.ExternalKey;
import com.me.security.externalkey.dto.ExternalKeyCheckResponse;
import com.me.security.externalkey.dto.KeyRegistrationRequest;
import com.me.security.externalkey.dto.KeyRegistrationResponse;
import com.me.security.externalkey.service.KeyManagementService;
import com.me.security.externalkey.service.KeyQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/key")
@RequiredArgsConstructor
public class ExternalKeyManageController {

    private final KeyManagementService managementService;

    private final KeyQueryService queryService;

    @PostMapping
    public KeyRegistrationResponse keyAdd(@Valid @RequestBody KeyRegistrationRequest request) {
        return managementService.keyRegistration(request);
    }

    @DeleteMapping("/{apiKey}")
    public SimpleResponse blockedKey(@PathVariable("apiKey") String apiKey) {
        managementService.keyDelete(apiKey);
        return SimpleResponse.ok();
    }

    @GetMapping("/{token}")
    public ExternalKeyCheckResponse check(@PathVariable("token") String token) {
        ExternalKey externalKey = queryService.findByKeyIfNoOptional(token);
        return new ExternalKeyCheckResponse(externalKey);
    }
}
