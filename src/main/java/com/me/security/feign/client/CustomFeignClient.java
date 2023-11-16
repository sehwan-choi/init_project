package com.me.security.feign.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(url = "${feign.url}", configuration = )
public class CustomFeignClient {
}
