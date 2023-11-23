package com.example.equipment_booker.service;

import com.example.equipment_booker.model.CompanyAdministratorAppeal;
import com.example.equipment_booker.model.CompanyAppeal;
import com.example.equipment_booker.repository.CompanyAdministratorAppealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyAdministratorAppealService {
    @Autowired
    private CompanyAdministratorAppealRepository companyAdministratorAppealRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private Environment env;

    public CompanyAdministratorAppeal save(CompanyAdministratorAppeal companyAdministratorAppeal) {
        return companyAdministratorAppealRepository.save(companyAdministratorAppeal);
    }

    public List<CompanyAdministratorAppeal> findAll() {
        return companyAdministratorAppealRepository.findAll();
    }

    public CompanyAdministratorAppeal findOne(Long id) {
        return companyAdministratorAppealRepository.findById(id).orElseGet(null);
    }

    public void sendEmailWithAnswer(CompanyAdministratorAppeal companyAdministratorAppeal) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(companyAdministratorAppeal.getRegisteredUser().getEmail());
        mailMessage.setFrom(env.getProperty("spring.mail.username"));
        mailMessage.setSubject("Answer to your appeal");
        mailMessage.setText("Your appeal to " + companyAdministratorAppeal.getRegisteredUser().getName() + " " + companyAdministratorAppeal.getRegisteredUser().getSurname() + ": \n\n" + companyAdministratorAppeal.getContent() + "\n\nAnswer: \n\n" + companyAdministratorAppeal.getAnswer());
        javaMailSender.send(mailMessage);
    }
}
