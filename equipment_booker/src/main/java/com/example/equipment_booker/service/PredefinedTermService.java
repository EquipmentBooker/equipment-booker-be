package com.example.equipment_booker.service;

import com.example.equipment_booker.model.*;
import com.example.equipment_booker.repository.PredefinedTermRepository;
import com.google.gson.Gson;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PredefinedTermService {

    @Autowired
    private PredefinedTermRepository predefinedTermRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private Environment env;
    @Transactional(readOnly = false)
    public PredefinedTerm save(PredefinedTerm predefinedTerm) {
        return predefinedTermRepository.save(predefinedTerm);
    }

    public List<PredefinedTerm> findFreePredefinedTermsByCompanyId(Long companyId, LocalDateTime currentDateTime, String status) {
        return predefinedTermRepository.findFreePredefinedTermsByCompanyId(companyId, currentDateTime, status);
    }
    public PredefinedTerm findOne(Long id) {
        return predefinedTermRepository.findById(id).orElseGet(null);
    }

    public void sendReservationEmail(RegisteredUser registeredUser, Term term, List<TermEquipment> termEquipments) throws Exception {
        ArrayList<String> termEquipment = new ArrayList<>();
        for (TermEquipment te: termEquipments) {
            termEquipment.add(te.getEquipment().getName() + " - quantity: " + te.getQuantity());
        }

        String qrCodeText = "Term Id: " + term.getId() + "\n"
                + "Term date: " + term.getStartTime().getDayOfMonth() + ". " + term.getStartTime().getMonth() + " " + term.getStartTime().getYear()  + ".\n"
                + "Term duration: " + term.getStartTime().getHour() + ":" + term.getStartTime().getMinute() + " - " + term.getStartTime().getHour() + ":" + term.getStartTime().plusMinutes(term.getDuration()).getMinute() + "\n"
                + "Company administrator: " + term.getCompanyAdministrator().getName() + " " + term.getCompanyAdministrator().getSurname() + "\n"
                + "Reserved equipment: \n" + String.join(", ", termEquipment);

        int width = 300;
        int height = 300;
        BufferedImage qrCodeImage = QRCodeService.generateQRCodeImage(qrCodeText, width, height);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(qrCodeImage, "png", baos);
        byte[] qrCodeBytes = baos.toByteArray();


        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(registeredUser.getEmail());
        helper.setFrom(env.getProperty("spring.mail.username"));
        helper.setSubject("Term reservation");
        helper.setText("Scan your QR code in attachment to see informations about reserved term.");
        helper.addAttachment("qrcode.png", new ByteArrayResource(qrCodeBytes), "image/png");
        javaMailSender.send(message);
    }
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public PredefinedTerm update(PredefinedTerm predefinedTerm) {
        return predefinedTermRepository.save(predefinedTerm);
    }

    public List<PredefinedTerm> findOverlappingTerms(CompanyAdministrator companyAdministrator, LocalDateTime startTime, LocalDateTime endTime) {
        return predefinedTermRepository.findOverlappingTerms(companyAdministrator, startTime, endTime);
    }
}
