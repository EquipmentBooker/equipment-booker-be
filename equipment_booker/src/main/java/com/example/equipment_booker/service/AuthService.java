package com.example.equipment_booker.service;

import com.example.equipment_booker.model.RegisteredUser;
import com.example.equipment_booker.repository.RegisteredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private RegisteredUserRepository registeredUserRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private Environment env;


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
}
