package com.smart_home.Authentication.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart_home.Authentication.DTO.AuthenticationTokenDTO;
import com.smart_home.Authentication.Model.User;
import com.smart_home.Authentication.Permission.Role;
import com.smart_home.Authentication.Repository.UserRepository;
import com.smart_home.Authentication.Request.UserLoginRequest;
import com.smart_home.Authentication.Request.UserRegistrationRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    private User user;
    @BeforeEach
    void setUp() {
        user = User.builder()
                .email("test@test.com")
                .password(passwordEncoder.encode("password"))
                .pin(passwordEncoder.encode("1234"))
                .role(Role.USER)
                .build();
        userRepository.save(user);
    }
    @Test
    void registrationUser_shouldRegisterNewUser() throws Exception {
        //Given
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setEmail("test2@test.com");
        userRegistrationRequest.setPassword("Password#");
        userRegistrationRequest.setPassword2("Password#");
        userRegistrationRequest.setPin("1234");

        //When/Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/account/registration/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRegistrationRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void loginUserViaPin_shouldLoginUserAndReturnAuthenticateToken() throws Exception{
        //Given
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("test@test.com");
        userLoginRequest.setPin("1234");

        //When
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/account/login/pin/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userLoginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        //Then
        String responseString = mvcResult.getResponse().getContentAsString();
        AuthenticationTokenDTO authenticationTokenDTO = objectMapper.readValue(responseString,AuthenticationTokenDTO.class);
        Assertions.assertNotNull(authenticationTokenDTO);
    }
    @Test
    void loginUserViaPin_shouldReturn400BadRequestForUserLogin() throws Exception{
        //Given
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("test2@test.com");
        userLoginRequest.setPin("1234");

        //When/Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/account/login/pin/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userLoginRequest)))
                .andExpect(status().isBadRequest());
    }
    @Test
    void loginUserViaPin_shouldReturn400BadRequestForUserPin() throws Exception{
        //Given
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("test@test.com");
        userLoginRequest.setPin("1235");

        //When/Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/account/login/pin/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userLoginRequest)))
                .andExpect(status().isBadRequest());
    }
}