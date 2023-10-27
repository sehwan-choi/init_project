package com.me.security.externalkey.service;

import com.me.security.externalkey.domain.ExternalKey;
import com.me.security.externalkey.dto.KeyRegistrationRequest;
import com.me.security.externalkey.dto.KeyRegistrationResponse;
import com.me.security.externalkey.exception.ApiKeyNotFoundException;
import com.me.security.externalkey.repository.ExternalKeyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExternalKeyManagementService implements KeyManagementService{

    private final ExternalKeyRepository keyRepository;

    @Qualifier("UUIDKeyGenerator")
    private final KeyGenerator keyGenerator;

    @Override
    public KeyRegistrationResponse keyRegistration(KeyRegistrationRequest request) {
        ExternalKey key = ExternalKey.createKeyFromRequest(request, keyGenerator);
        ExternalKey externalKey = keyRepository.save(key);
        return new KeyRegistrationResponse(externalKey.getCreatedAt(), externalKey.getApiKey());
    }

    @Override
    @Transactional
    public void keyDelete(String apiKey) {
        ExternalKey key = keyRepository.findByApiKey(apiKey).orElseThrow(() -> new ApiKeyNotFoundException(apiKey));
        key.block();
    }
}
