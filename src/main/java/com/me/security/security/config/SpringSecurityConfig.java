package com.me.security.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.me.security.common.generator.KeyGenerator;
import com.me.security.mvc.filter.LoggingFilter;
import com.me.security.security.filter.TokenAuthenticationFilter;
import com.me.security.security.provider.ResourceAccessDeniedHandler;
import com.me.security.security.provider.TokenAuthenticationEntryPoint;
import com.me.security.security.provider.TokenAuthenticationProvider;
import com.me.security.security.service.AccessAuthorizationService;
import com.me.security.security.service.AuthenticationTokenVerifier;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SpringSecurityConfig {

    private static final String LOGIN_PROCESSING_URL = "/api/v1/sign/signin";

    private static final String SIGN_UP_PROCESSING_URL = "/api/v1/sign/signup";

    @Value("${spring.security.debug:false}")
    boolean webSecurityDebug;


    @Setter(onMethod_ = @Autowired, onParam_ = @Qualifier("jwtAuthenticationTokenVerifier"))
    private AuthenticationTokenVerifier verifyService;

    @Setter(onMethod_ = @Autowired, onParam_ = @Qualifier("userAuthorizationService"))
    private AccessAuthorizationService authorizationService;

    private final ObjectMapper objectMapper;

    private final MessageSource messageSource;

    @Qualifier("logKeyGenerator")
    private final KeyGenerator keyGenerator;

    @Bean
    @Order(2)
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        http
                // ID, Password 문자열을 Base64로 인코딩하여 전달하는 구조
                .httpBasic(AbstractHttpConfigurer::disable)
                // 쿠키 기반이 아닌 JWT 기반이므로 사용하지 않음
                .csrf(AbstractHttpConfigurer::disable)
                // CORS 설정
                .cors(this::corsConfiguration)
                // Spring Security 세션 정책 : 세션을 생성 및 사용하지 않음
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .securityMatcher(new MvcRequestMatcher(introspector,"/api/**"))
                // 조건별로 요청 허용/제한 설정
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests
                            // 회원가입과 로그인은 모두 승인
                            .requestMatchers(new MvcRequestMatcher(introspector,LOGIN_PROCESSING_URL), new MvcRequestMatcher(introspector,SIGN_UP_PROCESSING_URL)).permitAll()
                                .requestMatchers(new MvcRequestMatcher(introspector, "/h2-console")).permitAll()
                                .requestMatchers(new MvcRequestMatcher(introspector,"/test/**")).permitAll()
                            // /admin으로 시작하는 요청은 ADMIN 권한이 있는 유저에게만 허용
//                                .requestMatchers("/admin/**").hasRole("ADMIN")
                            // /user 로 시작하는 요청은 USER 권한이 있는 유저에게만 허용
//                                .requestMatchers("/user/**").hasRole("USER")
                            .anyRequest().authenticated()
                )
                // 에러 핸들링
                .exceptionHandling(manager -> manager
                        .authenticationEntryPoint(authenticationEntryPoint())
                        .accessDeniedHandler(accessDeniedHandler())
                );

        http.apply(new TokenFilterConfigurer(new TokenAuthenticationProvider(verifyService, authorizationService),keyGenerator));


        return http.build();
    }

    public void corsConfiguration(CorsConfigurer<HttpSecurity> corsCustomizer) {
        CorsConfigurationSource source = request -> {
            // Cors 허용 패턴
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(
                    List.of("*")
            );
            config.setAllowedMethods(
                    List.of("*")
            );
            return config;
        };
        corsCustomizer.configurationSource(source);
    }

    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new TokenAuthenticationEntryPoint(objectMapper, messageSource);
    }

    public AccessDeniedHandler accessDeniedHandler() {
        return new ResourceAccessDeniedHandler(objectMapper, messageSource);
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.debug(webSecurityDebug);
//                .ignoring()
//                    .requestMatchers("/swagger-ui");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static class TokenFilterConfigurer extends AbstractHttpConfigurer<TokenFilterConfigurer, HttpSecurity> {
        private final TokenAuthenticationProvider tokenAuthenticationProvider;
        private final KeyGenerator keyGenerator;

        public TokenFilterConfigurer( TokenAuthenticationProvider tokenAuthenticationProvider,
                                        KeyGenerator keyGenerator) {
            this.tokenAuthenticationProvider = tokenAuthenticationProvider;
            this.keyGenerator = keyGenerator;
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http
                    .authenticationProvider(tokenAuthenticationProvider)
                    .addFilterBefore(new LoggingFilter(keyGenerator), CsrfFilter.class)
                    .addFilterBefore(new TokenAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);
        }
    }
}
