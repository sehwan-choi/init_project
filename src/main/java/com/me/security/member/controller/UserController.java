package com.me.security.member.controller;

import com.me.security.common.dto.SimpleResponse;
import com.me.security.member.domain.User;
import com.me.security.member.dto.UserCreateRequest;
import com.me.security.member.dto.UserFindResponse;
import com.me.security.member.service.UserCommandService;
import com.me.security.member.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserQueryService userQueryService;

    @GetMapping("/{userId}")
    public UserFindResponse getUser(@PathVariable(name = "userId") Long userId) {
        User user = userQueryService.findUserById(userId);
        return UserFindResponse.userToResponse(user);
    }
}
