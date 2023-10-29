package com.me.security.security.service.impl;

import com.me.security.externalkey.dto.ExternalKeyAttribute;
import com.me.security.externalkey.exception.ApiKeyNotFoundException;
import com.me.security.security.exception.ClientNotFoundAuthenticationException;
import com.me.security.security.exception.ClientStatusAuthenticationException;
import com.me.security.security.model.ClientAuthInfo;
import com.me.security.security.model.ReservedClientAuthInfo;
import com.me.security.security.service.AccessAuthorizationService;
import com.me.security.security.service.ExternalKeyStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservedAccessAuthorizationService implements AccessAuthorizationService {

    private final ExternalKeyStorage keyStorage;
    @Override
    public ClientAuthInfo getAccessClient(Long id) {
        try {
            ExternalKeyAttribute externalKey = keyStorage.findByIdIfNoOptional(id);
            checkAccess(externalKey);
            return new ReservedClientAuthInfo(externalKey.id(), externalKey.name(), null);
        }
        catch (ApiKeyNotFoundException e) {
            throw new ClientNotFoundAuthenticationException("Api Id \"" + id + "\" not found!", e);
        }
    }

    private void checkAccess(ExternalKeyAttribute attribute) {
        if (attribute.block()) {
            throw new ClientStatusAuthenticationException("key Id \"" + attribute.id() + "\" key \"" + attribute.apiKey() + "\" is blocked!");
        }

        if (LocalDateTime.now().isBefore(attribute.startDate()) || LocalDateTime.now().isAfter(attribute.endDate())) {
            throw new ClientStatusAuthenticationException("key Id \"" + attribute.id() + "\" key \"" + attribute.apiKey() + "\" is expired!");
        }
    }
}
