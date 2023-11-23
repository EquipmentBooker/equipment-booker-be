package com.example.equipment_booker.security.config;

import com.example.equipment_booker.security.service.CustomUserDetailsService;
import com.example.equipment_booker.service.CompanyAdministratorService;
import com.example.equipment_booker.service.RegisteredUserService;
import com.example.equipment_booker.service.SystemAdministratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class Config {
    private final RegisteredUserService registeredUserService;
    private final CompanyAdministratorService companyAdministratorService;
    private final SystemAdministratorService systemAdministratorService;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService(registeredUserService, companyAdministratorService, systemAdministratorService);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
