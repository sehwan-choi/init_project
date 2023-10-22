package com.me.security.member.service;

import com.me.security.member.domain.User;
import com.me.security.member.dto.UserCreateRequest;

public interface UserCommandService {

    User create(UserCreateRequest request);
}
