package com.me.security.member.service;

import com.me.security.member.domain.Authority;
import com.me.security.member.domain.User;
import com.me.security.member.domain.UserAuthority;
import com.me.security.member.dto.UserCreateRequest;
import com.me.security.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ModifyUserCommandService implements UserCommandService{

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User create(UserCreateRequest request) {
        return userRepository.save(new User(request.userName(), request.email(), request.password(), Set.of(Authority.USER)));
    }
}
