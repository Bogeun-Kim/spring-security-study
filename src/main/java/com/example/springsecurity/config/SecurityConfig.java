package com.example.springsecurity.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration // 설정파일
@RequiredArgsConstructor // 생성자주입
@EnableWebSecurity // Security 설정
public class SecurityConfig {

    // 암호화 처리를 위한 PasswordEncoder 객체
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 아이디와 패스워드의 일치 여부를 확인할 때 사용하는 객체
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration config) {
        return config.getAuthenticationManager();
    }

    // 필터 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http
                // CSRF 보호 비활성화
                .csrf(AbstractHttpConfigurer::disable)
                // HTTP 요청에 대한 접근 권한 설정
                .authorizeHttpRequests(auth -> {
                    // CORS Preflight 요청 허용
                    auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                    // 특정 경로는 무조건 허용
                    auth.requestMatchers("/login", "/signup").permitAll();
                    // Swagger API 문서 허용
                    auth.requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll();
                    // 이외의 경로들은 인증(로그인) 필요
                    auth.anyRequest().authenticated();
                })
                // 세션 방식을 사용하지 않음
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                // 기본 CORS 설정 사용
                .cors(cors -> {})
                // 직접 작성한 커스텀 필터인 JwtFIlter를 필터 체인에 추가 예정

                .build();
    }
}
