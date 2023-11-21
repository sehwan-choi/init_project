package com.me.security.mvc.domain;

import lombok.Getter;

public enum MDCKey {
    TRX_ID("request_id");

    @Getter
    private final String key;

    MDCKey(String key) {
        this.key = key;
    }
}
