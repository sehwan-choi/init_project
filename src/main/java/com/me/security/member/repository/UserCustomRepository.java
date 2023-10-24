package com.me.security.member.repository;

import com.me.security.member.domain.User;

import java.util.Optional;

public interface UserCustomRepository {

    Optional<User> findUserById(Long id);

    Optional<User> findByEmail(String email);

}
