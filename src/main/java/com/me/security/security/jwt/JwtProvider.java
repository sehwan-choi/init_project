package com.me.security.security.jwt;

import com.me.security.member.domain.Authority;
import com.me.security.security.service.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";

    private static final String AUTHORIZATION_PREFIX_NAME = "BEARER ";

    @Value("${jwt.secret.key}")
    private String salt;

    private Key secretKey;

    // 만료시간 : 1Hour
    private final long exp = 1000L * 60 * 60;

    private final CustomUserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
    }

    // 토큰 생성
    public String createToken(String account, List<Authority> roles) {
        Claims claims = Jwts.claims().setSubject(account);
        claims.put("roles", roles);
        Date date = new Date();
        Date expiredDate = new Date(date.getTime() + exp);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(expiredDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // JWT에서 권한 정보 획득
    // Spring Security 인증과정에서 권한확인을 위한 기능
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getAccount(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getAccount(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION_HEADER_NAME);
    }

    public boolean validateToken(String token) {
        try {
            String bearerToken;
            if (!token.substring(0, AUTHORIZATION_PREFIX_NAME.length()).equalsIgnoreCase(AUTHORIZATION_PREFIX_NAME)) {
                return false;
            } else {
                bearerToken = extractToken(token);
            }

            return tokenIsExpired(bearerToken);
        } catch (Exception e) {
            return false;
        }
    }

    public String extractToken(String token) {
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
