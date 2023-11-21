package com.me.security.externalkey.controller;

import com.me.security.externalkey.domain.ExternalKey;
import com.me.security.externalkey.dto.ExternalKeyCheckResponse;
import com.me.security.externalkey.service.KeyQueryService;
import com.me.security.security.model.ClientAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/external/api/v1/key")
@RequiredArgsConstructor
public class ExternalKeyManageController {

    private final KeyQueryService queryService;

    @GetMapping
    public ExternalKeyCheckResponse check() {
        ClientAuthenticationToken authentication = (ClientAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        ExternalKey externalKey = queryService.findByKeyIfNoOptional(authentication.getAccessToken());
        return new ExternalKeyCheckResponse(externalKey);
    }
}
