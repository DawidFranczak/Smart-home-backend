package com.smart_home.Validation.Validator;

import com.smart_home.Authentication.Repository.UserRepository;
import com.smart_home.Validation.Anotation.UniqueEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidate implements ConstraintValidator<UniqueEmail, String> {
    @Autowired
    UserRepository userRepository;
    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
    }
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext){
        return userRepository.findByEmail(email).isEmpty();
    }
}
