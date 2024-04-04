package com.smart_home.Authentication.Service;

import com.smart_home.Authentication.DTO.AuthenticationTokenDTO;
import com.smart_home.Authentication.DTOMapper.AuthenticationTokenDTOMapper;
import com.smart_home.Authentication.Model.User;
import com.smart_home.Authentication.Permission.Role;
import com.smart_home.Authentication.Repository.TokenRepository;
import com.smart_home.Authentication.Repository.UserRepository;
import com.smart_home.Authentication.Request.UserLoginRequest;
import com.smart_home.Authentication.Request.UserRegistrationRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private AuthenticationTokenDTOMapper authenticationTokenDTOMapper;

    private User user;
    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .email("Test@test2.com")
                .password("password")
                .pin("1234")
                .role(Role.USER)
                .build();
    }

    @Test
    public void userRegistration_shouldSaveUserIntoDataBase(){
        //Given
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setPassword("password");
        userRegistrationRequest.setPassword2("password");
        userRegistrationRequest.setPin("1234");
        userRegistrationRequest.setEmail("test@test.pl");

        //When
        userService.userRegistration(userRegistrationRequest);

        //Then
        verify(userRepository,times(1)).save(any(User.class));
    }

    @Test
    public void shouldReturnAuthenticationTokenViaPin(){
        //Given
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setPassword("password");
        userLoginRequest.setPin("1234");
        userLoginRequest.setEmail("Test@test2.com");

        AuthenticationTokenDTO authenticationTokenDTO = AuthenticationTokenDTO.builder()
                .token("jwt-token")
                .permission("USER")
                .build();
        //When
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("jwt-token");
        when(tokenRepository.findAllValidTokensByUser(any())).thenReturn(new ArrayList<>());
        when(tokenRepository.save(any())).thenReturn(null);
        when(authenticationTokenDTOMapper.apply("jwt-token",Role.USER)).thenReturn(authenticationTokenDTO);

        AuthenticationTokenDTO viaPassword = userService.userLoginViaPin(userLoginRequest);

        //Then

        Assertions.assertEquals(viaPassword.token(),"jwt-token");
        Assertions.assertEquals(viaPassword.permission(),"USER");
    }
}