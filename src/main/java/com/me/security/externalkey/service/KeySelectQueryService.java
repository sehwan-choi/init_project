package com.me.security.externalkey.service;

import com.me.security.externalkey.domain.ExternalKey;
import com.me.security.externalkey.exception.ApiKeyNotFoundException;
import com.me.security.externalkey.repository.ExternalKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KeySelectQueryService implements KeyQueryService {

    private final ExternalKeyRepository keyRepository;


    @Override
    public List<ExternalKey> findAllKey() {
        return keyRepository.findAll();
    }

    @Override
    public Optional<ExternalKey> findByKey(String key) {
        return keyRepository.findByApiKey(key);
    }

    @Override
    public ExternalKey findByKeyIfOptional(String key) {
        return keyRepository.findByApiKey(key).orElseThrow(() -> new ApiKeyNotFoundException(key));
    }

    @Override
    public Optional<ExternalKey> findById(Long id) {
        return keyRepository.findById(id);
    }

    @Override
    public ExternalKey findByIdIfOptional(Long id) {
        return keyRepository.findById(id).orElseThrow(() -> new ApiKeyNotFoundException(id));
    }
}
