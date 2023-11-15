package com.example.equipment_booker.service;

import com.example.equipment_booker.dto.JwtAuthenticationRequest;
import com.example.equipment_booker.dto.JwtAuthenticationResponse;
import com.example.equipment_booker.model.RegisteredUser;
import com.example.equipment_booker.repository.RegisteredUserRepository;
import com.example.equipment_booker.security.service.CustomUserDetailsService;
import com.example.equipment_booker.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Autowired
    private RegisteredUserRepository registeredUserRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private Environment env;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;


    public void sendConfirmationEmail(RegisteredUser registeredUser) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(registeredUser.getEmail());
        mailMessage.setFrom(env.getProperty("spring.mail.username"));
        mailMessage.setSubject("Account confirmation");
        mailMessage.setText("Click the link to confirm your account: "
                + "http://localhost:8080/api/auth/confirm?token=" + registeredUser.getConfirmationToken());
        javaMailSender.send(mailMessage);
    }

    public boolean confirmAccount(String confirmationToken) {
        RegisteredUser registeredUser = registeredUserRepository.findByConfirmationToken(confirmationToken);
        if (registeredUser != null) {
            registeredUser.setActivated(true);
            registeredUserRepository.save(registeredUser);
            return true;
        }
        return false;
    }

    public JwtAuthenticationResponse authenticate(JwtAuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = customUserDetailsService.loadUserByUsername(request.getEmail());
        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return new JwtAuthenticationResponse(accessToken, refreshToken);
    }
}
