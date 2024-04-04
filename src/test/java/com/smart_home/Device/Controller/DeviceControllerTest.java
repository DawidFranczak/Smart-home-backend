package com.smart_home.Device.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart_home.Authentication.Model.Token;
import com.smart_home.Authentication.Model.User;
import com.smart_home.Authentication.Permission.Role;
import com.smart_home.Authentication.Permission.TokenType;
import com.smart_home.Authentication.Repository.TokenRepository;
import com.smart_home.Authentication.Repository.UserRepository;
import com.smart_home.Authentication.Service.JwtService;
import com.smart_home.Device.Request.AddDeviceRequest;
import com.smart_home.Exception.NotFound404Exception;
import com.smart_home.UDP.UDP;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class DeviceControllerTest {

    @LocalServerPort
    private int port;

    @MockBean
    private UDP udp;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    private User user;
    private String authToken;
    private DatagramPacket datagramPacket;

    @BeforeEach
    public void setUp () throws UnknownHostException {
        user = User.builder()
                .role(Role.USER)
                .build();

        Token token = Token.builder()
                .user(user)
                .token(jwtService.generateToken(user))
                .tokenType(TokenType.BEARER)
                .expired(false)
                .build();

        user.setTokens(List.of(token));
        userRepository.save(user);
        tokenRepository.save(token);
        authToken = "Bearer " + user.getTokens().get(0).getToken();

        byte[] data = "avnrhewirb78943qnvpofdpgfv".getBytes();
        datagramPacket = new DatagramPacket(
                data,
                data.length,
                InetAddress.getByName("192.168.0.12"),
                1111
        );
    }

    @Test
    @WithMockUser
    void addNewDevice_shouldAddNewDevice() throws Exception {
        //Given
        AddDeviceRequest form = new AddDeviceRequest();
        form.setName("Akwarium");
        form.setType("Aquarium");

        //When
        when(udp.scanLocalHost(
                any(String.class),
                any(Integer.class),
                any(Integer.class),
                any(String.class)))
                .thenReturn(datagramPacket);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/device/add/")
                        .header("Authorization", authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isCreated());

    }

    @Test
    @WithMockUser
    void addNewDevice_shouldReturnInvalidName() throws Exception {
        //Given
        AddDeviceRequest form = new AddDeviceRequest();
        form.setName("Akwarium");
        form.setType("Aquarium");

        //When
        when(udp.scanLocalHost(
                any(String.class),
                any(Integer.class),
                any(Integer.class),
                any(String.class)))
                .thenReturn(datagramPacket);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/device/add/")
                        .header("Authorization", authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/device/add/")
                        .header("Authorization", authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void addNewDevice_shouldThrowNotFound404Exception() throws Exception {
        //Given
        AddDeviceRequest form = new AddDeviceRequest();
        form.setName("Akwarium");
        form.setType("Aquarium");

        //When
        when(udp.scanLocalHost(
                any(String.class),
                any(Integer.class),
                any(Integer.class),
                any(String.class)))
                .thenThrow(NotFound404Exception.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/device/add/")
                        .header("Authorization", authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(form)))
                .andExpect(status().isNotFound());
    }
}