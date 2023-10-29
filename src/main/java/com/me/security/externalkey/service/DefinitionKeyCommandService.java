package com.me.security.externalkey.service;

import com.me.security.externalkey.domain.ExternalKey;
import com.me.security.externalkey.repository.ExternalKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefinitionKeyCommandService implements KeyCommandService{

    private final ExternalKeyRepository keyRepository;

    @Override
    @Transactional
    public ExternalKey save(ExternalKey key) {
        return keyRepository.save(key);
    }

    @Override
    @Transactional
    public List<ExternalKey> saveAll(Collection<ExternalKey> keys) {
        return keyRepository.saveAll(keys);
    }
}
