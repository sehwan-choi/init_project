package com.me.security.externalkey.service;

import com.me.security.externalkey.domain.ExternalKey;

import java.util.List;
import java.util.Optional;

public interface KeyQueryService {

    List<ExternalKey> findAllKey();

    Optional<ExternalKey> findByKey(String key);

    ExternalKey findByKeyIfOptional(String key);

    Optional<ExternalKey> findById(Long id);

    ExternalKey findByIdIfOptional(Long id);
}
