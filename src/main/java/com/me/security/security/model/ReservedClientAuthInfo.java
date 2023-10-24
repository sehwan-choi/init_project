package com.me.security.security.model;

import com.me.security.member.domain.Authority;
import lombok.Builder;

import java.util.List;

@Builder
public record ReservedClientAuthInfo(
        Long reservedId,
        String reservedName,
        List<Authority> roles) implements ClientAuthInfo {
    @Override
    public Long getId() {
        return this.reservedId;
    }

    @Override
    public String getName() {
        return this.reservedName;
    }

    @Override
    public List<Authority> getRoles() {
        return this.roles;
    }
}
