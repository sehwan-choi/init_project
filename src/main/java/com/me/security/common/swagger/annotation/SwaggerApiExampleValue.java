package com.me.security.common.swagger.annotation;

import com.me.security.common.code.ServerCode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SwaggerApiExampleValue {
    ServerCode value();
}
