package com.me.security.security.service;

import com.me.security.security.model.ClientAuthInfo;

public interface AccessAuthorizationService {

    ClientAuthInfo getAccessClient(Long id);
}
