package com.example.saju.saju.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // ⚠️ 테스트용: CSRF 비활성화 (나중에 켜도 됨)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register", "/css/**", "/js/**", "/images/**").permitAll() // 회원가입, 정적 리소스 접근 허용
                        .anyRequest().authenticated() // 나머지는 로그인 필요
                )
                .formLogin(login -> login
                        .loginPage("/login")               // ✅ 커스텀 로그인 페이지 지정
                        .loginProcessingUrl("/login")      // POST /login 처리 경로
                        .defaultSuccessUrl("/", true)      // 로그인 성공 시 메인으로
                        .failureUrl("/login?error=true")   // 실패 시 다시 로그인 페이지
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")        // 로그아웃 시 로그인 페이지로
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
