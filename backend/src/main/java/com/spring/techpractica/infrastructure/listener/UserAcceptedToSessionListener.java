package com.spring.techpractica.infrastructure.listener;

import com.spring.techpractica.core.session.event.UserAcceptedToSessionEvent;
import com.spring.techpractica.infrastructure.mail.sender.MailSender;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class UserAcceptedToSessionListener {
    private final MailSender mailSender;

    @EventListener
    public void handleUserAcceptedToSession(UserAcceptedToSessionEvent event) throws MessagingException {
        log.info("UserAcceptedToSessionEvent received for user {}", event.userId());

        String emailReceiver = event.email();
        mailSender.sendMail(emailReceiver, "You Have Been Accepted!",
                createHtmlPage(event));

        log.info("Sent acceptance email to {}", event.email());
    }

    private static String createHtmlPage(UserAcceptedToSessionEvent event) {
        return String.format("""
                <html>
                    <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                        <div style="max-width: 600px; margin: auto; background-color: white; padding: 30px; border-radius: 8px; text-align: center;">
                            <h2>Congratulations, %s!</h2>
                            <p>You have been accepted to the session <strong>%s</strong>.</p>
                            <p>We look forward to seeing you there!</p>
                            <p style="font-size:12px; color:#aaa;">If you did not expect this email, you can ignore it.</p>
                        </div>
                    </body>
                </html>
                """, event.name(), event.sessionName());
    }
}
