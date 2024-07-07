package com.me.security.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ServerCode {

    ALREADY_SIGNUP(HttpStatus.BAD_REQUEST, "0001", "code.signup.already"),
    CANNOT_SIGNUP(HttpStatus.BAD_REQUEST, "0002", "code.signup.cannot"),
    LOGIN_FAIL(HttpStatus.BAD_REQUEST, "0003", "code.login.fail"),
    SIGNUP_DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "0004", "code.duplicate.email"),
    API_KEY_NOT_FOUND(HttpStatus.BAD_REQUEST, "0005", "code.not.found.api.key"),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "0006", "code.not.found.user"),
    KEY_GENERATION_FAIL(HttpStatus.BAD_REQUEST, "0007", "code.key.generation.fail"),

    //공통 코드
    SUCCESS(HttpStatus.OK, "0200", "code.success"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "0404", "code.not.found"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "0400", "code.bad.request"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "0403", "code.forbidden"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "0401", "code.unauthorized"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "0500", "code.internal.error");

    private final HttpStatus httpStatus;

    private final String code;

    private final String messageCode;
}
