package com.example.first_club.membership.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    @Value("${api.key.name:X-API-KEY}")
    private String apiKeyHeaderName;

    @Value("${api.key.value:ayush}")
    private String apiKeyValue;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String apiKey = request.getHeader(apiKeyHeaderName);

        if (apiKeyValue.equals(apiKey)) {
            filterChain.doFilter(request, response); // ✅ Valid API key
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Invalid or missing API key");
        }
    }
}

