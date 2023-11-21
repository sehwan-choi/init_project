package com.me.security.feign;

import com.me.security.feign.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "testFeign", url = "https://jsonplaceholder.typicode.com", configuration = FeignConfiguration.class)
public interface TestFeign {

    @GetMapping("/posts")
    List<Object> getPosts();

    @GetMapping("/posts/{id}")
    Object getPost(@PathVariable int id);

    @GetMapping("/comments")
    List<Object> getComments();

    @GetMapping("/comments/{id}")
    Object getComment(@PathVariable int id);
}
