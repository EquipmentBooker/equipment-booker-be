package com.example.equipment_booker.security.config;

import com.example.equipment_booker.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .cors()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers( "/api/companies").permitAll()
                .requestMatchers("/api/registered_users/**").permitAll()
                .requestMatchers(HttpMethod.GET,"/api/registered_users/email/**").hasRole("REGISTERED_USER")
                .requestMatchers(HttpMethod.GET, "/api/companies/**").permitAll()
                .requestMatchers("/api/company_administrators/**").permitAll()
                .requestMatchers("/api/predefined_terms/**").permitAll()
                .requestMatchers(HttpMethod.GET,"/api/predefined_terms/company/**").hasRole("REGISTERED_USER")
                .requestMatchers(HttpMethod.PUT,"/api/predefined_terms/schedule/**").hasRole("REGISTERED_USER")
                .requestMatchers("/api/company_administrators/email/**").hasRole("COMPANY_ADMINISTRATOR")
                .requestMatchers("/api/terms/scheduled/registered-user/**").hasRole("REGISTERED_USER")
                .requestMatchers("/api/terms/cancel/**").hasRole("REGISTERED_USER")
                .requestMatchers("/api/terms/free/company/**").hasRole("REGISTERED_USER")
                .requestMatchers("/api/terms/schedule/**").hasRole("REGISTERED_USER")
                .requestMatchers("/api/terms/past-scheduled/**").hasRole("REGISTERED_USER")
                .requestMatchers(HttpMethod.POST, "/api/appeals").hasRole("REGISTERED_USER")
                .requestMatchers(HttpMethod.POST, "/api/system_administrators/**").permitAll()
                .requestMatchers("/api/system_administrators/email/**").hasRole("SYSTEM_ADMINISTRATOR")
                .requestMatchers(HttpMethod.GET,"/api/appeals").hasRole("SYSTEM_ADMINISTRATOR")
                .requestMatchers(HttpMethod.PUT,"/api/appeals/**").hasRole("SYSTEM_ADMINISTRATOR")
                .requestMatchers(HttpMethod.POST,"/api/company_administrator_appeals").hasRole("REGISTERED_USER")
                .requestMatchers(HttpMethod.GET,"/api/company_administrator_appeals").hasRole("SYSTEM_ADMINISTRATOR")
                .requestMatchers(HttpMethod.PUT,"/api/company_administrator_appeals/**").hasRole("SYSTEM_ADMINISTRATOR")
                .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
