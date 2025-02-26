package com.yourstech.springform.service.impl;

import com.yourstech.springform.exception.EmailSendException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public void sendVerificationEmail(String to, String subject, String verificationLink) throws MessagingException {
        Context context = new Context();
        context.setVariable("verificationLink", verificationLink);

        String htmlContent = templateEngine.process("verification-email", context);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

        try {
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(htmlContent, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new EmailSendException("Failed to send email to " + to, e);
        }
    }
}