package com.me.security.member.service;

import com.me.security.member.domain.User;

import java.util.Optional;

public interface UserQueryService {

    User findUserById(Long id);

    User findUserByEmail(String email);
}
