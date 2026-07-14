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

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration // 설정파일
@RequiredArgsConstructor // 생성자주입
@EnableWebSecurity // Security 설정
public class SecurityConfig {
    // SecurityFilterChain 메서드를 활용한 CustomizedSecurityFilterChain
    // 해당 메서드가 구현되면 Default로 실행되고 있던 내부 SecurityFilterChain은 비활성화(Back-off) 된다.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
                // CSRF 보호 비활성화
                .csrf(AbstractHttpConfigurer::disable)
                // HTTP 요청에 대한 접근 권한 설정
                .authorizeHttpRequests(requests -> {
                    requests.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();                                    // CORS Preflight 요청 허용
                    requests.requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated(); // 인증 필요
                    requests.requestMatchers("/notices", "/contact", "/error").permitAll();  // runtime Error 오류페이지도 보여주기                                 // 모두 접근 허용
                    requests.requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll();  // Swagger API 문서 허용
                    requests.anyRequest().permitAll(); // 이외의 경로들은 허용
                })
                .formLogin(withDefaults())
                .httpBasic(withDefaults())
//                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS)) // 세션 방식을 사용하지 않음
                .cors(cors -> {}); // 기본 CORS 설정 사용
                // 직접 작성한 커스텀 필터인 JwtFIlter를 필터 체인에 추가 예정
        return http.build();
    }
}
