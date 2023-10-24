package com.me.security.security.service.impl;

import com.me.security.security.model.ClientAuthInfo;
import com.me.security.security.service.AccessAuthorizationService;
import org.springframework.stereotype.Service;

@Service
public class ReservedAccessAuthorizationService implements AccessAuthorizationService {

    @Override
    public ClientAuthInfo getAccessClient(Long id) {
        return null;
    }
}
