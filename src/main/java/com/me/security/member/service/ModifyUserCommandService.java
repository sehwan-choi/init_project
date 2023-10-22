package com.me.security.member.service;

import com.me.security.member.domain.User;
import com.me.security.member.dto.UserCreateRequest;
import com.me.security.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModifyUserCommandService implements UserCommandService{

    private final UserRepository userRepository;

    @Override
    public User create(UserCreateRequest request) {
        return userRepository.save(new User(request.userName(), request.email(), request.password()));
    }
}
