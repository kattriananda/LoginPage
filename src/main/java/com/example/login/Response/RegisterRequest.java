package com.example.login.Response;

import com.example.login.Validation.UniqueEmail;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @NotEmpty(message = "First name must not be empty")
    @NotNull(message = "First name must not be null")
    private String firstName;

    @NotEmpty(message = "Last name must not be empty")
    @NotNull(message = "Last name must not be null")
    private String lastName;

    @Email(message = "Email must be a well-formed email address")
    @NotEmpty(message = "Email must not be empty")
    @NotNull(message = "Email must not be null")
    @UniqueEmail(message = "Email is already registered")
    private String email;

    @NotEmpty(message = "Password must not be empty")
    @NotNull(message = "Password must not be null")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must be at least 8 characters long, contain one uppercase letter, one lowercase letter, one number, and one special character")
    private String password;

}
