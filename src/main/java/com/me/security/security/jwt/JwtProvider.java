package com.me.security.security.jwt;

import com.me.security.member.domain.Authority;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collection;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider implements JwtService {

    private static final String AUTHORIZATION_PREFIX_NAME = "Bearer ";

    @Setter
    private String authorizationPrefixName = AUTHORIZATION_PREFIX_NAME;

    private final String salt;

    private Key secretKey;

    // 만료시간 : 1Hour
    private final long exp;

    public JwtProvider(@Value("${jwt.secret.key}") String salt, @Value("${jwt.secret.expired}") long exp) {
        this.salt = salt;
        this.exp = exp;
    }

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
    }

    // 토큰 생성
    @Override
    public String createToken(String account, Collection<Authority> roles) {
        Claims claims = Jwts.claims().setSubject(account);
        claims.put("roles", roles);
        Date date = new Date();
        Date expiredDate = new Date(date.getTime() + exp);
        return authorizationPrefixName + Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(expiredDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String getAccount(String token) {
        String jwtToken = getJwt(token);
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            String jwt = getJwt(token);
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    private boolean checkJwtPrefix(String token) {
        return token.startsWith(authorizationPrefixName);
    }

    private String getJwt(String token) {
        if (!checkJwtPrefix(token)) {
            throw new MalformedJwtException("유효하지 않는 JWT Prefix 입니다.");
        }
        return token.substring(7);
    }
}
