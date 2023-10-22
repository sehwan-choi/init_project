package com.me.security.member.service;

import com.me.security.member.domain.User;
import com.me.security.member.dto.UserFindResponse;
import com.me.security.member.exception.UserNotFoundException;
import com.me.security.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SelectUserQueryService implements UserQueryService{

    private final UserRepository userRepository;
    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User findUserByUserName(String userName) {
        return userRepository.findByName(userName).orElseThrow(() -> new UserNotFoundException(userName));
    }
}
