package com.schedular.config;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SecurityConfig {

    private JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable();
        http.addFilterBefore(jwtRequestFilter, AuthorizationFilter.class);
        http.authorizeHttpRequests().
                anyRequest().permitAll();
//                .requestMatchers(HttpMethod.POST,"/api/v1/users/registration","/api/v1/users/login").permitAll()
//                .requestMatchers(HttpMethod.GET,"/api/v1/users/").permitAll()
//                .requestMatchers(HttpMethod.POST,"/api/v1/users/").hasRole("USER")
//                .anyRequest().permitAll();
        return http.build();
    }
}
