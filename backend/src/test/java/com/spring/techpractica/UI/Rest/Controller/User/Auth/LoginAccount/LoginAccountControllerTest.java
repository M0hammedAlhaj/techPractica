package com.spring.techpractica.UI.Rest.Controller.User.Auth.LoginAccount;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.techpractica.Core.User.Service.PasswordEncryptor;
import com.spring.techpractica.Core.User.User;
import com.spring.techpractica.Core.User.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LoginAccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncryptor passwordEncryptor;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String LOGIN_PATH = "/api/v1/auth/login";

    @BeforeEach
    void setup() {
        // Clear users before each test to avoid duplicates
        userRepository.deleteAll();

        // Insert a test user with encoded password (must match your security config)
        User user = User.builder()
                .name("Test User")
                .email("email@example.com")
                .password(passwordEncryptor.hash("password"))
                .build();

        userRepository.save(user);
    }

    @Test
    void should_login_successfully_and_return_token() throws Exception {
        LoginAccountRequest request = new LoginAccountRequest("email@example.com", "password");

        mockMvc.perform(post(LOGIN_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login account successful"))
                .andExpect(jsonPath("$.data.token").isNotEmpty())
                .andExpect(jsonPath("$.data.user.name").value("Test User"))
                .andExpect(jsonPath("$.data.user.email").value("email@example.com"));
    }

    @Test
    void should_return_401_when_invalid_credentials() throws Exception {
        LoginAccountRequest request = new LoginAccountRequest("email@example.com", "wrong-password");

        mockMvc.perform(post(LOGIN_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Invalid email or password"));
    }

    @Test
    void should_return_400_when_request_is_invalid() throws Exception {
        LoginAccountRequest request = new LoginAccountRequest("email.com", "");

        mockMvc.perform(post(LOGIN_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}