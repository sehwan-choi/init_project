package com.me.security.externalkey.service;

import com.me.security.externalkey.dto.KeyRegistrationRequest;
import com.me.security.externalkey.dto.KeyRegistrationResponse;

public interface KeyManagementService {

    KeyRegistrationResponse keyRegistration(KeyRegistrationRequest request);

    void keyDelete(String apiKey);
}
