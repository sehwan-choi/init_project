package com.me.security.security.controller;

import com.me.security.common.dto.SimpleResponse;
import com.me.security.security.service.ExternalKeyStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/resource")
@RestController
@RequiredArgsConstructor
public class ResourceController {

    private final ExternalKeyStorage storage;

    @PostMapping("/reload")
    public SimpleResponse reload() {
        storage.reload();
        return SimpleResponse.ok();
    }
}
