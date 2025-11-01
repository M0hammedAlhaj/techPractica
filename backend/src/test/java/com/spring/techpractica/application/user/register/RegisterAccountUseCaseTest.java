package com.spring.techpractica.application.user.register;

import com.spring.techpractica.application.user.auth.register.RegisterAccountCommand;
import com.spring.techpractica.application.user.auth.register.RegisterAccountUseCase;
import com.spring.techpractica.core.role.model.RoleType;
import com.spring.techpractica.core.user.User;
import com.spring.techpractica.core.user.UserFactory;
import com.spring.techpractica.core.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegisterAccountUseCaseTest {
    @InjectMocks
    RegisterAccountUseCase underTest;

    @Mock
    UserRepository userRepository;

    @Mock
    UserFactory userFactory;

    @Mock
    ApplicationEventPublisher eventPublisher;

    @Test
    public void user_should_register_account_successfully() {
        RegisterAccountCommand request =
                new RegisterAccountCommand("name", "email", "password");

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(request.password())
                .build();

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userFactory.create(request.name(), request.email(), request.password(), RoleType.ROLE_USER)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        assertDoesNotThrow(() -> {
            User result = underTest.execute(request);
            assertNotNull(result);
            assertEquals("name", result.getName());
            assertEquals("email", result.getEmail());
            assertEquals("password", result.getPassword());
        });

        verify(userFactory).create(request.name(), request.email(), request.password(), RoleType.ROLE_USER);
        verify(userRepository).existsByEmail(user.getEmail());
        verify(userRepository).save(user);
    }

    @Test
    public void user_should_not_save() {
        RegisterAccountCommand request =
                new RegisterAccountCommand("name", "email", "password");

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(request.password())
                .build();

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        underTest.execute(request);

        verify(userRepository, never()).save(user);
        verify(eventPublisher, never()).publishEvent(any(RegisterAccountCommand.class));
    }
}