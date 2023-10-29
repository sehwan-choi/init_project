package com.me.security.member.service;

import com.me.security.member.domain.User;

import java.util.Optional;

public interface UserQueryService {

    Optional<User> findUserById(Long id);

    Optional<User> findUserByEmail(String email);

    User findUserByIdIfNoOptional(Long id);

    User findUserByEmailIfNoOptional(String email);
}
