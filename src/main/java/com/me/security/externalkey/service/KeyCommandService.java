package com.me.security.externalkey.service;

import com.me.security.externalkey.domain.ExternalKey;

import java.util.Collection;
import java.util.List;

public interface KeyCommandService {

    ExternalKey save(ExternalKey key);

    List<ExternalKey> saveAll(Collection<ExternalKey> keys);
}
