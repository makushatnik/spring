package com.cdesign.spittr.data.service;

import com.cdesign.spittr.config.ConstantManager;
import com.cdesign.spittr.data.entity.Spitter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by RealXaker on 31.08.2016.
 */
public class EmailService {

    @Inject
    private JavaMailSender mailSender;

    public void sendWelcomeEmail(Spitter spitter) throws MessagingException {
        if (mailSender == null) return;

        sendEmailWithAttachment(spitter.getEmail(),
                ConstantManager.WELCOME_SUBJECT,
                ConstantManager.WELCOME_TEXT);
    }

    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(ConstantManager.SERVER_EMAIL);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public void sendEmailWithAttachment(String to, String subject, String text) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(ConstantManager.SERVER_EMAIL);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);
        helper.addAttachment("coupon", new ClassPathResource("/images/coupon.jpg"));
        mailSender.send(message);
    }
}
