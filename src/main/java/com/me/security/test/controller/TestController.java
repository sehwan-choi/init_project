package com.me.security.test.controller;

import com.me.security.common.dto.SimpleResponse;
import com.me.security.test.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping("/logger/async")
    public SimpleResponse asyncLogger() {
        for (int i = 0 ; i < 10 ; i ++) {
            testService.test(i+1);
        }
        return SimpleResponse.ok();
    }
}
