package com.me.security.externalkey.domain;

import com.me.security.externalkey.dto.KeyRegistrationRequest;
import com.me.security.externalkey.service.KeyGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ExternalKey {

    private static final Long KEY_DEFAULT_DURATION = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String apiKey;

    private String name;

    private boolean block;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public ExternalKey(String name, KeyGenerator keyGenerator) {
        this.name = name;
        this.apiKey = keyGenerator.generate();
        this.block = false;
        setDefaultDate();
    }

    public ExternalKey(String name, LocalDateTime startDate, LocalDateTime endDate, KeyGenerator keyGenerator) {
        this.name = name;
        this.apiKey = keyGenerator.generate();
        this.block = false;
        if (startDate == null || endDate == null) {
            setDefaultDate();
        } else if (startDate.isAfter(endDate) || endDate.isBefore(startDate)) {
            setDefaultDate();
        }else {
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }

    public void setDefaultDate() {
        this.startDate = LocalDateTime.now();
        this.endDate = LocalDateTime.now().plusDays(KEY_DEFAULT_DURATION);
    }

    public static ExternalKey createKeyFromRequest(KeyRegistrationRequest request, KeyGenerator keyGenerator) {
        String name = request.name();

        return new ExternalKey(name, request.startTime(), request.endTime(), keyGenerator);
    }

    public void block() {
        this.block = true;
    }
}
