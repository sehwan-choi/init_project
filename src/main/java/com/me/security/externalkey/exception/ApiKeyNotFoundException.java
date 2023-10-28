package com.me.security.externalkey.exception;

import com.me.security.common.exception.ResourceNotFoundException;

public class ApiKeyNotFoundException extends ResourceNotFoundException {

    private final String apiKey;
    private final String message;
    public ApiKeyNotFoundException(String apiKey) {
        super();
        this.apiKey = apiKey;
        this.message = "The api key \"" + apiKey + "\" cannot be found!";
    }
    public ApiKeyNotFoundException(Long apiId) {
        super();
        this.apiKey = String.valueOf(apiId);
        this.message = "The key Id \"" + apiKey + "\" cannot be found!";
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
