package com.me.security.common.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionCommonCode {

    public static final String RESOURCE_NOT_FOUND_ERROR = "error.resource.not.found";
    public static final String UNAUTHORIZED_ERROR = "error.authorized";
    public static final String FORBIDDEN_ERROR = "error.forbidden";
    public static final String INTERNAL_SERVER_ERROR = "error.internal.server";
    public static final String BAD_REQUEST_ERROR = "error.invalid.data";
}
