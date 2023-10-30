package com.me.security.security.service;

import com.me.security.externalkey.dto.ExternalKeyAttribute;

import java.util.Optional;

public interface ExternalKeyStorage {

    void reload();

    void load();

    ExternalKeyAttribute findByIdIfNoOptional(Long id);

    Optional<ExternalKeyAttribute> findById(Long id);

    Optional<ExternalKeyAttribute> findByKey(String token);
}
