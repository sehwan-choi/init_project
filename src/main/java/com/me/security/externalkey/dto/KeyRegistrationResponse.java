package com.me.security.externalkey.dto;

import java.time.LocalDateTime;

public record KeyRegistrationResponse(LocalDateTime createdAt,
                                      String apiKey) {
}
