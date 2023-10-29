package com.me.security.member.service;

import com.me.security.member.domain.User;
import com.me.security.member.exception.UserNotFoundException;
import com.me.security.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SelectUserQueryService implements UserQueryService{

    private final UserRepository userRepository;
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserById(Long id) {
        return userRepository.findUserById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserByIdIfNoOptional(Long id) {
        return userRepository.findUserById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User findUserByEmailIfNoOptional(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }
}
