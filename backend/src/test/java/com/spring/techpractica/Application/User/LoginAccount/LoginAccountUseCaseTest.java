package com.spring.techpractica.Application.User.LoginAccount;

import com.spring.techpractica.Application.User.Auth.LoginAccount.LoginAccountCommand;
import com.spring.techpractica.Application.User.Auth.LoginAccount.LoginAccountUseCase;
import com.spring.techpractica.Core.Shared.Exception.ResourcesNotFoundException;
import com.spring.techpractica.Core.User.Exception.UserAuthenticationException;
import com.spring.techpractica.Core.User.Service.PasswordEncryptor;
import com.spring.techpractica.Core.User.User;
import com.spring.techpractica.Core.User.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginAccountUseCaseTest {
    @InjectMocks
    LoginAccountUseCase underTest;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncryptor passwordEncryptor;

    @Test
    public void user_should_login_successful() {
        // Arrange
        LoginAccountCommand request = new LoginAccountCommand("email", "password");

        User user = User.builder()
                .name("name")
                .email("email")
                .password("hashed")
                .build();

        when(userRepository.findByEmail("email")).thenReturn(Optional.of(user));
        when(passwordEncryptor.matches("password", "hashed")).thenReturn(true);

        // Act & Assert
        assertDoesNotThrow(() -> {
            User result = underTest.execute(request);
            assertNotNull(result);
            assertEquals("name", result.getName());
            assertEquals("email", result.getEmail());
        });

        verify(userRepository).findByEmail("email");
        verify(passwordEncryptor).matches("password", "hashed");
    }

    @Test
    public void user_should_not_login_invalid_password() {
        LoginAccountCommand request = new LoginAccountCommand("email", "password");

        User user = User.builder()
                .name("name")
                .email("email")
                .password("hashed")
                .build();

        when(userRepository.findByEmail("email")).thenReturn(Optional.of(user));
        when(passwordEncryptor.matches("password", "hashed")).thenReturn(false);

        assertThrows(UserAuthenticationException.class, () -> underTest.execute(request));

        verify(userRepository).findByEmail("email");
        verify(passwordEncryptor).matches("password", "hashed");
    }

    @Test
    public void user_should_not_login_invalid_email() {
        LoginAccountCommand request = new LoginAccountCommand("email", "password");
        when(userRepository.findByEmail("email")).thenReturn(Optional.empty());

        assertThrows(ResourcesNotFoundException.class, () -> underTest.execute(request));

        verify(userRepository).findByEmail("email");
        verify(passwordEncryptor, never()).matches(anyString(), anyString());
    }
}