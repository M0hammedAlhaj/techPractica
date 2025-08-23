package com.spring.techpractica.infrastructure.MailSender;

import jakarta.mail.MessagingException;

public interface MailSender {
    void sendMail(String emailReceiver, String emailSubject, String message) throws MessagingException;
}
