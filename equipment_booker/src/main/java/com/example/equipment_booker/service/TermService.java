package com.example.equipment_booker.service;

import com.example.equipment_booker.model.*;
import com.example.equipment_booker.repository.TermRepository;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TermService {

    @Autowired
    private TermRepository termRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private Environment env;
    @Transactional(readOnly = false)
    public Term save(Term term) {
        return termRepository.save(term);
    }
    public Term findOne(Long id) {
        return termRepository.findById(id).orElseGet(null);
    }
    public List<Term> findScheduledTermsByRegisteredUserId(Long registeredUserId, LocalDateTime currentTime, String status) {
        return termRepository.findScheduledTermsByRegisteredUserId(registeredUserId, currentTime, status);
    }
    public List<Term> findFreeTermsByCompanyId(Long companyId, LocalDateTime currentTime, String status, boolean isPredefined) {
        List<Term> freeTerms = new ArrayList<Term>();

        for (Term t: termRepository.findAll()) {
            if (t.isPredefined() == isPredefined && status.equals(t.getStatus()) && t.getStartTime().isAfter(currentTime) && t.getCompanyAdministrator().getCompany().getId() == companyId) {
                freeTerms.add(t);
            }
        }

        return freeTerms;
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

    public List<Term> findPastScheduledTermsByRegisteredUserId(Long registeredUserId, LocalDateTime currentTime, String status) {
        return termRepository.findPastScheduledTermsByRegisteredUserId(registeredUserId, currentTime, status);
    }
}
