package com.me.security.security.service.impl;

import com.me.security.externalkey.dto.ExternalKeyAttribute;
import com.me.security.externalkey.exception.ApiKeyNotFoundException;
import com.me.security.externalkey.service.KeyQueryService;
import com.me.security.security.service.ExternalKeyStorage;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InMemoryExternalKeyStorage implements ExternalKeyStorage {

    private final Map<Long, ExternalKeyAttribute> storage = new ConcurrentHashMap<>();
    private final KeyQueryService queryService;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        load();
    }
    @Override
    public void reload() {
        storage.clear();
        load();
    }

    @Override
    public void load() {
        Map<Long, ExternalKeyAttribute> contributeMap = queryService.findAllKey().stream().map(ExternalKeyAttribute::new).collect(Collectors.toMap(ExternalKeyAttribute::id, Function.identity()));
        storage.putAll(contributeMap);
    }

    @Override
    public ExternalKeyAttribute findByIdIfNoOptional(Long id) {
        Optional<ExternalKeyAttribute> externalKeyAttribute = getKeyById(id);
        if (externalKeyAttribute.isPresent()) {
            return externalKeyAttribute.get();
        }
        throw new ApiKeyNotFoundException(id);
    }

    @Override
    public Optional<ExternalKeyAttribute> findById(Long id) {
        return getKeyById(id);
    }

    private Optional<ExternalKeyAttribute> getKeyById(Long id) {
        ExternalKeyAttribute externalKeyAttribute = storage.get(id);
        if (externalKeyAttribute != null) {
            return Optional.of(externalKeyAttribute);
        }
        return Optional.empty();
    }

    @Override
    public Optional<ExternalKeyAttribute> findByKey(String token) {
        return getKeyByToken(token);
    }

    private Optional<ExternalKeyAttribute> getKeyByToken(String token) {
        return storage.values().stream().filter(f -> f.apiKey().equals(token)).findAny();
    }


}
