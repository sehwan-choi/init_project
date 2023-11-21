package com.me.security.feign.client;

import com.me.security.feign.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "customFeignClient", url = "${feign.url}", configuration = FeignConfiguration.class)
public interface CustomFeignClient {

}
