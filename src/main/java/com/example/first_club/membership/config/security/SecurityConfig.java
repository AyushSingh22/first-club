package com.example.first_club.membership.config.security;

import io.lettuce.core.dynamic.annotation.Value;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ApiKeyAuthFilter apiKeyAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) //using stateless APIs might remove when JWT/authentication cookies  by browser, for now securing with X-API-KEY
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/memberships/**").authenticated()
                        .anyRequest().permitAll()
                )
                .addFilterBefore(apiKeyAuthFilter, BasicAuthenticationFilter.class);
        return http.build();
    }
}
