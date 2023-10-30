package com.me.security.security.jwt;

import com.me.security.member.domain.Authority;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
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

    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";

    private static final String AUTHORIZATION_PREFIX_NAME = "bearer ";

    @Setter
    private String authorizationHeaderName = AUTHORIZATION_HEADER_NAME;

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
        String jwtToken = extractToken(token);
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject();
    }

    private String resolveToken(HttpServletRequest request) {
        return request.getHeader(authorizationHeaderName);
    }

    @Override
    public boolean validateToken(String token) {
        try {
            String bearerToken;
            if (checkJwtPrefix(token)) {
                bearerToken = extractToken(token);
            } else {
                return false;
            }

            return tokenIsExpired(bearerToken);
        } catch (ExpiredJwtException e) {
            log.error("Jwt Token Expired!", e);
            return false;
        } catch (Exception e) {
            log.error("Jwt Parsing Error", e);
            return false;
        }
    }

    private boolean checkJwtPrefix(String token) {
        return token.substring(0, authorizationPrefixName.length()).equalsIgnoreCase(authorizationPrefixName);
    }

    private String extractToken(String token) {
        return token.split(" ")[1].trim();
    }

    private boolean tokenIsExpired(String bearerToken) {
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(bearerToken);

        return !claims
                .getBody()
                .getExpiration()
                .before(new Date());
    }

}
