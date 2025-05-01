package com.spring.techpractica.service;

import com.spring.techpractica.dto.otp.OtpResponse;
import com.spring.techpractica.model.entity.Otp;
import com.spring.techpractica.mengmentData.OtpManagementData;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailSenderService {


    private static final String SUBJECT_EMAIL_RESET_PASSWORD = "RESET_PASSWORD";

    private static final String SUBJECT_EMAIL_SUBMIT_EMAIL = "SUBMIT_EMAIL";

    private final JavaMailSender javaMailSender;

    private final OtpManagementData otpManagementData;

    public void sendResetPassword(OtpResponse otpResponse) {

        SimpleMailMessage message = new SimpleMailMessage();

        Otp otp = otpManagementData
                .getOtpByOtpId(otpResponse.getOtpId());

        message.setTo(otpResponse.getUserEmail());
        message.setSubject(SUBJECT_EMAIL_RESET_PASSWORD);

        message.setText("This is your Otp " +
                otp.getOtpCode() + "\n\n"
                + "the code will expire in 5 minutes.");
        javaMailSender.send(message);
    }


}
