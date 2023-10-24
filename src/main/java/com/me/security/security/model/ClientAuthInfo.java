package com.me.security.security.model;

import com.me.security.member.domain.Authority;

import java.util.List;

public interface ClientAuthInfo {

    Long getId();

    String getName();

    List<Authority> getRoles();
}
