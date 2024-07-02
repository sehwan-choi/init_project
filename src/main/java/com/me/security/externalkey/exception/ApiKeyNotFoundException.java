package com.me.security.externalkey.exception;

import com.me.security.common.code.ServerCode;
import com.me.security.common.exception.ResourceNotFoundException;

public class ApiKeyNotFoundException extends ResourceNotFoundException {

    public ApiKeyNotFoundException(String apiKey) {
        super(ServerCode.API_KEY_NOT_FOUND, "The api key \"" + apiKey + "\" cannot be found!");
    }
    public ApiKeyNotFoundException(Long apiId) {
        super(ServerCode.API_KEY_NOT_FOUND, "The key Id \"" + apiId + "\" cannot be found!");
    }
}
