package com.me.security.security.model;

import com.me.security.member.domain.Authority;
import lombok.Builder;

import java.util.List;

@Builder
public record UserClientAuthInfo(
        Long userId,
        String userName,
        List<Authority> roles) implements ClientAuthInfo {
    @Override
    public Long getId() {
        return this.userId;
    }

    @Override
    public String getName() {
        return this.userName;
    }

    @Override
    public List<Authority> getRoles() {
        return this.roles;
    }
}