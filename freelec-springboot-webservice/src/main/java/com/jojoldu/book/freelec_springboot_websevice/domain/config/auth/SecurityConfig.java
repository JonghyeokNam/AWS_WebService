package com.jojoldu.book.freelec_springboot_websevice.domain.config.auth;

import com.jojoldu.book.freelec_springboot_websevice.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // (1) csrf 비활성화
                .csrf(csrf -> csrf.disable())
                // (2) h2-console 접근 위해 X-Frame-Options 비활성화
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))

                // (3) 인증/인가 설정
                .authorizeHttpRequests(authorize -> authorize
                        // 특정 요청에 대해서는 인증 없이 접근 허용
                        .requestMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                        // 특정 패턴의 API에 대해서는 USER 권한 필요
                        .requestMatchers("/api/v1/**").hasRole(Role.USER.name())
                        // 그 외 모든 요청은 인증 필요
                        .anyRequest().authenticated()
                )

                // (4) 로그아웃 설정
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                )

                // (5) OAuth2 로그인 설정
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                );

        // (6) SecurityFilterChain 객체를 반환
        return http.build();
    }
}

