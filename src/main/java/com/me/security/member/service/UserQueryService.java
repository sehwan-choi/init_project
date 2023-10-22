package com.me.security.member.service;

import com.me.security.member.domain.User;
import com.me.security.member.dto.UserFindResponse;

public interface UserQueryService {

    User findUserById(Long id);

    User findUserByUserName(String userName);
}
