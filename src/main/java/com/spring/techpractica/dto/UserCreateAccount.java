package com.spring.techpractica.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateAccount {

    @Size(min = 3, max = 20)
    @NotNull
    @Pattern(regexp = "")
    private String name;

    @Size(min = 3, max = 18)
    @NotNull
    private String firstName;

    @Size(min = 3, max = 18)
    @NotNull
    private String lastName;

    @Email
    @Size(max = 320)
    @NotNull
    private String userEmail;

    @NotNull
    @Size(min = 8, max = 100)
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one uppercase, one lowercase, one digit, and one special character."
    )
    private String userPassword;


}
