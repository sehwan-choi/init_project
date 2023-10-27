package com.me.security.externalkey.exception;

import com.me.security.common.exception.ResourceNotFoundException;

public class ApiKeyNotFoundException extends ResourceNotFoundException {

    private final String apiKey;
    public ApiKeyNotFoundException(String apiKey) {
        super();
        this.apiKey = apiKey;
    }

    @Override
    public String getMessage() {
        return "The key \"" + apiKey + "\" cannot be found!";
    }
}
