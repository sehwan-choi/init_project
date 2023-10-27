package com.me.security.externalkey.repository;

import com.me.security.externalkey.domain.ExternalKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExternalKeyRepository extends JpaRepository<ExternalKey, Long> {

    Optional<ExternalKey> findByApiKey(String key);
}
