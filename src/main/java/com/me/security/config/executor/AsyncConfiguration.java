package com.me.security.config.executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfiguration {

    @Bean
    public ThreadPoolTaskExecutor asyncThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);    //생성해서 사용할 스레드 풀에 속한 기본 스레드 갯수(default : 1)
        taskExecutor.setMaxPoolSize(10);    //최대 스레드 갯수 (default : Integer.MAX_VALUE (약 21억))
        taskExecutor.setQueueCapacity(20);  //이벤트 대기 큐 크기 (default : Integer.MAX_VALUE (약 21억))
        taskExecutor.setThreadNamePrefix("Async-");
        taskExecutor.setTaskDecorator(new MDCCopyTaskDecorator());// 기존 스레드의 로그키값이 담긴 MDC request_id 정보가 Async 스레드에서 수행될 스레드의 MDC 안에 복제한다.
        return taskExecutor;
    }
}


