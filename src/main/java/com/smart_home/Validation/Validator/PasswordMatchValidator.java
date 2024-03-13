package com.smart_home.Validation.Validator;

import com.smart_home.Authentication.Request.UserRegistrationRequest;
import com.smart_home.Validation.Anotation.PasswordMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, UserRegistrationRequest> {
    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
    }
    @Override
    public boolean isValid(UserRegistrationRequest userRegistrationForm, ConstraintValidatorContext constraintValidatorContext) {
        String password = userRegistrationForm.getPassword();
        String password2 = userRegistrationForm.getPassword2();
        if(password.equals(password2)) return true;
        constraintValidatorContext.buildConstraintViolationWithTemplate("Passwords don't match.")
                .addPropertyNode("password2")
                .addConstraintViolation();
        return false;
    }
}
