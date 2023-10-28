package com.me.security.externalkey.controller;

import com.me.security.common.dto.SimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/external/api/v1/key")
@RequiredArgsConstructor
public class ExternalController {

    @GetMapping
    public SimpleResponse key() {
        return SimpleResponse.ok();
    }

}
