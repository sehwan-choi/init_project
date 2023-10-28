package com.me.security.test.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TestService {

    @Async("asyncThreadPoolTaskExecutor")
    public void test(int index) {
        log.info("test" + index);
    }
}
