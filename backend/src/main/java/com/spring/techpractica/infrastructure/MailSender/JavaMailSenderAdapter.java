package com.spring.techpractica.infrastructure.MailSender;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JavaMailSenderAdapter implements MailSender {
    private final JavaMailSender mailSender;

    @Async
    @Override
    public void sendMail(String emailReceiver, String emailSubject, String message) throws MessagingException {
        MimeMessage messageHtml = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(messageHtml, true, "UTF-8");

        helper.setTo(emailReceiver);
        helper.setSubject(emailSubject);
        helper.setText(message, true);

        mailSender.send(messageHtml);
    }
}
