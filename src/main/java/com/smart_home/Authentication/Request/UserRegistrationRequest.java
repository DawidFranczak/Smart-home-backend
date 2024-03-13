package com.smart_home.Authentication.Request;

import com.smart_home.Validation.Anotation.PasswordMatch;
import com.smart_home.Validation.Anotation.UniqueEmail;
import jakarta.validation.constraints.*;
import lombok.Data;


@Data
@PasswordMatch
public class UserRegistrationRequest {
    @NotBlank(message = "First name can't be blank")
    private String firstName;

    @NotBlank(message = "Last name can't be blank")
    private String lastName;

    @NotBlank(message = "Email can't be blank")
    @Email(message = "Valid email")
    @UniqueEmail
    private String email;

    @NotBlank(message = "Password can't be blank")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*()\\-_=+{};:,<.>]).{8,}$",
            message = "The password must contain at least 8 characters, including 1 uppercase letter and 1 special character.")
    private String password;

    private String password2;

    @NotBlank(message = "Pin can't be blank")
    @Pattern(regexp = "^[0-9]{4}$",
            message = "Pin should by between 0000-9999")
    private String pin;

}
