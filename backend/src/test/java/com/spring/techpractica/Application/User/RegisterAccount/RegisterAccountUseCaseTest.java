package com.spring.techpractica.Application.User.RegisterAccount;

import com.spring.techpractica.Core.User.Exception.EmailAlreadyUsedException;
import com.spring.techpractica.Core.User.User;
import com.spring.techpractica.Core.User.UserFactory;
import com.spring.techpractica.Core.User.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
                new RegisterAccountCommand("name","email", "password");

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(request.password())
                .build();

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userFactory.create(request.name(), request.email(), request.password())).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        assertDoesNotThrow(() -> {
            User result = underTest.execute(request);
            assertNotNull(result);
            assertEquals("name", result.getName());
            assertEquals("email", result.getEmail());
            assertEquals("password", result.getPassword());
        });

        verify(userFactory).create(request.name(), request.email(), request.password());
        verify(userRepository).existsByEmail(user.getEmail());
        verify(userRepository).save(user);
    }

    @Test
    public void should_throw_email_already_used_exception_when_email_already_exists() {
        RegisterAccountCommand request =
                new RegisterAccountCommand("name","email", "password");

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(request.password())
                .build();

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        assertThrows(EmailAlreadyUsedException.class, () -> {
            underTest.execute(request);
        });

        verify(userRepository).existsByEmail(user.getEmail());
    }
}