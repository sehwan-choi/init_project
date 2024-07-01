package com.me.security.common.crypto.config;

import com.me.security.common.crypto.AESCrypto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AESHexCryptoConfig {

    @Value("${common.aes.key}")
    private String aesKey;

    @Bean
    public AESCrypto aesCrypto() {
        return new AESCrypto(aesKey);
    }
}
