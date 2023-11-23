package com.example.equipment_booker.service;

import com.example.equipment_booker.model.CompanyAdministrator;
import com.example.equipment_booker.model.CompanyAppeal;
import com.example.equipment_booker.model.RegisteredUser;
import com.example.equipment_booker.model.Term;
import com.example.equipment_booker.repository.CompanyAppealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyAppealService {
    @Autowired
    private CompanyAppealRepository companyAppealRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private Environment env;

    public CompanyAppeal save(CompanyAppeal companyAppeal) {
        return companyAppealRepository.save(companyAppeal);
    }

    public List<CompanyAppeal> findAll() {
        return companyAppealRepository.findAll();
    }

    public CompanyAppeal findOne(Long id) {
        return companyAppealRepository.findById(id).orElseGet(null);
    }

    public void sendEmailWithAnswer(CompanyAppeal companyAppeal) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(companyAppeal.getRegisteredUser().getEmail());
        mailMessage.setFrom(env.getProperty("spring.mail.username"));
        mailMessage.setSubject("Answer to your appeal");
        mailMessage.setText("Your appeal: \n\n" + companyAppeal.getContent() + "\n\nAnswer: \n\n" + companyAppeal.getAnswer());
        javaMailSender.send(mailMessage);
    }
}
