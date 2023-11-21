package com.me.security.feign;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableFeignClients
public class FeignTest {

    @Autowired
    TestFeign testFeign;

    @Test
    void test() {
        Object post = testFeign.getPost(1);
        System.out.println("post = " + post);
    }

    @Test
    void test2() {
        List<Object> posts = testFeign.getPosts();
        System.out.println("posts = " + posts);
    }

    @Test
    void test3() {
        testFeign.getComment(LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void test4() {
        testFeign.getComment(LocalDate.now(), LocalDate.now());
    }
}
