package com.spring.techpractica.infrastructure.Event;

import com.spring.techpractica.Core.User.Event.UserRegistrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserRegistration {

    @EventListener
    public void handleUserRegistrationEvent(UserRegistrationEvent userRegistrationEvent) {
        log.info("UserRegistrationEvent received");
        log.info("saved User{}", userRegistrationEvent);
        log.info("sent email for otp ...");
    }
}
