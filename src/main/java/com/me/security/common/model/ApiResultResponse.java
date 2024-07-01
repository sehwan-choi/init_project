package com.me.security.common.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.me.security.common.code.ServerCode;
import com.me.security.common.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ApiResultResponse<T> {

    @Schema(description = "성공/실패 코드")
    private final String code;

    @Schema(description = "성공/실패 메세지 SUCCESS 인 경우 성공")
    private final String message;

    @Schema(description = "데이터 정보")
    private final T data;

    @JsonCreator
    @Builder
    public ApiResultResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResultResponse<T> success() {
        return ApiResultResponse.ofResponse(ServerCode.SUCCESS.getCode(), "SUCCESS", null);
    }


    public static <T> ApiResultResponse<T> success(T data) {
        return ApiResultResponse.ofResponse(ServerCode.SUCCESS.getCode(), "SUCCESS", data);
    }

    public static <T> ApiResultResponse<T> ofResponse(String code, String message) {
        return ApiResultResponse.ofResponse(code, message,null);
    }

    public static <T> ApiResultResponse<T> error(String code, String message, T error) {
        return ApiResultResponse.ofResponse(code, message, error);
    }

    public static <T> ApiResultResponse<T> ofResponse(String code, String message, T data) {
        return ApiResultResponse.<T>builder()
                .code(code)
                .message(message)
                .data(data)
                .build();
    }
}
