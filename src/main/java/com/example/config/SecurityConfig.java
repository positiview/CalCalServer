package com.example.config;

import com.example.handler.CustomAuthFailureHandler;
import com.example.service.AdminMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AdminMemberService adminMemberService;

    // Spring Security 6.1.0부터는 메서드 체이닝의 사용을 지양하고 람다식을 통해 함수형으로 설정하게 지향하고 있음
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 페이지 권한 설정, 로그인 페이지 설정, 로그아웃 메소드 등에 대한 설정을 작성
        http
                .csrf((csrfConfig) -> csrfConfig.disable())
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                        .requestMatchers("/dashboard/**","/user/**","/exercise/**").authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin((formLogin) -> formLogin
                        .loginPage("/adminLogin")
                        .usernameParameter("loginId")
                        .passwordParameter("password")
                        .loginProcessingUrl("/adminLogin")
                        .failureHandler(simpleUrlAuthenticationFailureHandler())
                        .defaultSuccessUrl("/", true)
                )
                .logout((logoutConfig) -> logoutConfig
                        .logoutUrl("/adminLogout")
                        .logoutSuccessUrl("/adminLogin")
                )
                .userDetailsService(adminMemberService);

        ;

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCryptPasswordEncoder의 해시 함수를 이용해서 비밀번호 암호화
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler simpleUrlAuthenticationFailureHandler() {
        return new CustomAuthFailureHandler();
    }
}
