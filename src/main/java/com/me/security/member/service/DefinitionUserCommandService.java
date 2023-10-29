package com.me.security.member.service;

import com.me.security.member.domain.Authority;
import com.me.security.member.domain.User;
import com.me.security.member.dto.UserCreateRequest;
import com.me.security.member.exception.UserEmailDuplicateException;
import com.me.security.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class DefinitionUserCommandService implements UserCommandService{

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User create(UserCreateRequest request) {
        checkEmailDuplicate(request.email());
        return userRepository.save(new User(request.userName(), request.email(), request.password(), Set.of(Authority.USER)));
    }

    private void checkEmailDuplicate(String email) {
        userRepository.findByEmail(email).ifPresent(m -> {
            throw new UserEmailDuplicateException(m.getId(), m.getEmail());
        });
    }
}
