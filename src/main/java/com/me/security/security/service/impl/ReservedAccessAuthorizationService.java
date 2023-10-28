package com.me.security.security.service.impl;

import com.me.security.externalkey.domain.ExternalKey;
import com.me.security.externalkey.exception.ApiKeyNotFoundException;
import com.me.security.externalkey.service.KeyQueryService;
import com.me.security.security.exception.ClientNotFountAuthenticationException;
import com.me.security.security.model.ClientAuthInfo;
import com.me.security.security.model.ReservedClientAuthInfo;
import com.me.security.security.service.AccessAuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservedAccessAuthorizationService implements AccessAuthorizationService {

    private final KeyQueryService service;
    @Override
    public ClientAuthInfo getAccessClient(Long id) {
        try {
            ExternalKey externalKey = service.findByIdIfOptional(id);
            return new ReservedClientAuthInfo(externalKey.getId(), externalKey.getName(), null);
        }
        catch (ApiKeyNotFoundException e) {
            throw new ClientNotFountAuthenticationException("Api Id \"" + id + "\" not found!", e);
        }
    }
}
