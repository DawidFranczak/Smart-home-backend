package com.smart_home.Authentication.Service;

import com.smart_home.Authentication.Model.User;
import com.smart_home.Authentication.Repository.UserRepository;
import com.smart_home.Authentication.Request.UserRegistrationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void userRegistration_shouldSaveUserIntoDataBase(){
        //Given
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setFirstName("Test");
        userRegistrationRequest.setLastName("Test");
        userRegistrationRequest.setPassword("password");
        userRegistrationRequest.setPassword2("password");
        userRegistrationRequest.setPin("1234");
        userRegistrationRequest.setEmail("test@test.pl");

        //When
        userService.userRegistration(userRegistrationRequest);

        //Then
        verify(userRepository,times(1)).save(any(User.class));
    }
}