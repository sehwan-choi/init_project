package com.me.security.member.controller;

import com.me.security.member.domain.User;
import com.me.security.member.dto.UserFindResponse;
import com.me.security.member.service.UserQueryService;
import com.me.security.security.model.ClientAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserQueryService userQueryService;

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserFindResponse getUser() {
        ClientAuthenticationToken authentication = (ClientAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        User user = userQueryService.findUserByIdIfNoOptional(authentication.getAuthInfo().getId());
        return UserFindResponse.userToResponse(user);
    }
}
