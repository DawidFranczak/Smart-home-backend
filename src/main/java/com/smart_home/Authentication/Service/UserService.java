package com.smart_home.Authentication.Service;

import com.smart_home.Authentication.Model.User;
import com.smart_home.Authentication.Repository.UserRepository;
import com.smart_home.Authentication.Request.UserRegistrationRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void userRegistration(UserRegistrationRequest form) {
        User user = User.builder()
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .email(form.getEmail())
                .password(passwordEncoder.encode(form.getPassword()))
                .pin(passwordEncoder.encode(form.getPin().toString()))
                .build();
        userRepository.save(user);
    }
}
