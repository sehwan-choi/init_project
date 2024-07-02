package com.me.security.member.controller;

import com.me.security.common.code.ServerCode;
import com.me.security.common.model.ApiResultResponse;
import com.me.security.common.swagger.annotation.SwaggerApiExampleValues;
import com.me.security.member.domain.User;
import com.me.security.member.dto.UserFindResponse;
import com.me.security.member.service.UserQueryService;
import com.me.security.security.model.ClientAuthenticationToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "사용자 관련 API", description = "사용자 관련 API")
public class UserController {

    private final UserQueryService userQueryService;

    @GetMapping
    @SwaggerApiExampleValues({
            ServerCode.SUCCESS,
            ServerCode.USER_NOT_FOUND
    })
    @Operation(summary = "로그인된 사용자 정보 가져오는 API", description = "로그인된 사용자 정보 가져오는 API")
    public ApiResultResponse<UserFindResponse> getUser() {
        ClientAuthenticationToken authentication = (ClientAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        User user = userQueryService.findUserByIdIfNoOptional(authentication.getAuthInfo().getId());
        return ApiResultResponse.success(UserFindResponse.userToResponse(user));
    }
}
