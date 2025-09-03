package com.spring.techpractica.infrastructure.Listener;

import com.spring.techpractica.Core.User.Event.UserRegistrationEvent;
import com.spring.techpractica.infrastructure.Jwt.JwtGeneration;
import com.spring.techpractica.infrastructure.MailSender.MailSender;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class UserRegistration {
    private final JwtGeneration jwtGeneration;
    private final MailSender mailSender;

    @EventListener
    public void handleUserRegistrationEvent(UserRegistrationEvent event) throws MessagingException {
        log.info("UserRegistrationEvent received");
        log.info("Saved User {}", event.userId());

        String emailReceiver = event.email();
        UUID id = event.userId();
        String token = jwtGeneration.generateToken(id, emailReceiver);

        mailSender.sendMail(emailReceiver, "Verify Your Account", createHtmlPage(event, token));

        log.info("Sent verification email with HTML button to {}", event.email());
    }

    private static String createHtmlPage(UserRegistrationEvent event, String token) {
        String url = String.format("http://localhost:3030/active-account?token=%s", token);

        return String.format("""
                    <html>
                      <body style="margin:0; padding:0; font-family: Arial, sans-serif; background-color: #f4f4f4;">
                        <table role="presentation" style="width:100%%; border-collapse:collapse; border:0; border-spacing:0; background-color:#f4f4f4;">
                          <tr>
                            <td align="center" style="padding:20px;">
                              <table role="presentation" style="width:100%%; max-width:600px; background-color:#ffffff; border-radius:8px; padding:30px; box-shadow:0 2px 6px rgba(0,0,0,0.1);">
                                <tr>
                                  <td style="text-align:center;">
                                    <!-- Logo -->
                                    <img src="https://yourdomain.com/logo.png" alt="Logo" style="width:120px; margin-bottom:20px;" />
                
                                    <h2 style="color:#333; margin-bottom:10px;">Welcome, %s!</h2>
                                    <p style="color:#555; font-size:16px; line-height:1.5; margin-bottom:30px;">
                                      Thank you for registering. Please verify your account by clicking the button below.
                                    </p>
                
                                    <!-- Button -->
                                    <a href="%s" style="
                                        background-color:#4CAF50;
                                        color:#ffffff;
                                        padding:14px 28px;
                                        text-decoration:none;
                                        border-radius:6px;
                                        font-size:16px;
                                        display:inline-block;
                                        font-weight:bold;">
                                      ✅ Verify Account
                                    </a>
                
                                    <!-- Fallback link -->
                                    <p style="color:#999; font-size:14px; margin-top:30px;">
                                      If the button above doesn’t work, copy and paste this link into your browser:
                                    </p>
                                    <p style="word-break:break-all; font-size:14px; color:#4CAF50;">
                                      <a href="%s" style="color:#4CAF50; text-decoration:none;">%s</a>
                                    </p>
                
                                    <p style="color:#aaa; font-size:12px; margin-top:20px;">
                                      If you didn’t request this, you can safely ignore this email.
                                    </p>
                                  </td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                        </table>
                      </body>
                    </html>
                """, event.name(), url, url, url);
    }
}