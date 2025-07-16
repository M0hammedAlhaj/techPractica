package com.spring.techpractica.service.user;

import com.spring.techpractica.dto.otp.NewPassword;
import com.spring.techpractica.dto.otp.OtpResponse;
import com.spring.techpractica.dto.otp.UserEmailSendOtp;
import com.spring.techpractica.dto.otp.UserSubmitOtp;
import com.spring.techpractica.dto.userRegestation.UserCreateAccount;
import com.spring.techpractica.dto.userRegestation.UserLogin;
import com.spring.techpractica.exception.AuthenticationException;
import com.spring.techpractica.exception.ResourcesNotFoundException;
import com.spring.techpractica.model.entity.User;
import com.spring.techpractica.repository.UserRepository;
import com.spring.techpractica.service.JwtService;
import com.spring.techpractica.service.otp.OtpService;
import com.spring.techpractica.service.user.createAccount.UserAccountService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final OtpService otpService;

    private final UserAccountService userAccountService;

    public UserService(JwtService jwtService, UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       @Lazy OtpService otpService,
                       UserAccountService userAccountService) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.otpService = otpService;
        this.userAccountService = userAccountService;
    }


    @Transactional
    public void createAccount(UserCreateAccount userCreateAccount) {
        userAccountService.createAccount(userCreateAccount);
    }


    @Transactional
    public String userLogin(UserLogin userLogin) {

        User user = findUserByUserEmail(userLogin.getUserEmail());

        if (!passwordEncoder.matches(userLogin.getUserPassword(), user.getUserPassword())) {
            throw new AuthenticationException("Wrong password");
        }
        return jwtService.generateToken(user.getUserEmail(), user.getUserName());
    }

    public OtpResponse userCreateOtpCode(UserEmailSendOtp userEmailSendOtp) {
        return otpService.createOtp(userEmailSendOtp);
    }

    public String userSubmitOtp(UserSubmitOtp userSubmitOtp) {
        otpService.validationOtp(userSubmitOtp);
        return jwtService.generateToken(userSubmitOtp.getUserEmail());
    }


    public User findUserByUserEmail(String userEmail) {
        return userRepository.findUserByUserEmail(userEmail)
                .orElseThrow(() -> new ResourcesNotFoundException("User not found"));

    }

    public void userChangePassword(String userEmail,
                                   NewPassword newPassword) {

        User user = findUserByUserEmail(userEmail);

        if (!newPassword.getPassword().equals(newPassword.getConfirmPassword())) {
            throw new AuthenticationException("Wrong password");
        }

        user.setUserPassword(passwordEncoder.encode(newPassword.getPassword()));
        userRepository.save(user);
    }


}
