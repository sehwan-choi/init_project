package com.me.security.member.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Singular
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_id"))
    private Set<UserAuthority> roles = new HashSet<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private boolean blocked;

    public User(String name, String email, String password, Set<Authority> roles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.status = UserStatus.ACTIVE;
        roles.forEach(this::addAuthority);
        this.blocked = false;
    }

    public boolean isActive() {
        return status.equals(UserStatus.ACTIVE);
    }

    public boolean isBlocked() {
        return this.blocked;
    }
    public void addAuthority(Authority authority) {
        if (authority != null) {
            roles.add(new UserAuthority(authority, LocalDateTime.now()));
        }
    }

    public List<Authority> getAuthorities() {
        return this.getRoles().stream()
                .map(UserAuthority::getAuthority)
                .toList();
    }

    public void removeAuthority(Authority authority) {
        this.roles.removeIf(f -> f.getAuthority().equals(authority));
    }
}
