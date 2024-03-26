package com.smart_home.Authentication.Service;

import com.smart_home.Authentication.DTO.AuthenticationTokenDTO;
import com.smart_home.Authentication.DTOMapper.AuthenticationTokenDTOMapper;
import com.smart_home.Authentication.Model.Token;
import com.smart_home.Authentication.Model.User;
import com.smart_home.Authentication.Permission.Role;
import com.smart_home.Authentication.Permission.TokenType;
import com.smart_home.Authentication.Repository.TokenRepository;
import com.smart_home.Authentication.Repository.UserRepository;
import com.smart_home.Authentication.Request.UserLoginRequest;
import com.smart_home.Authentication.Request.UserRegistrationRequest;
import com.smart_home.Exception.CustomValidationException;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@EnableScheduling
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationTokenDTOMapper authenticationTokenDTOMapper;
    private final TokenRepository tokenRepository;


    public UserService(
            PasswordEncoder passwordEncoder,
            UserRepository userRepository,
            JwtService jwtService,
            AuthenticationTokenDTOMapper authenticationTokenDTOMapper,
            TokenRepository tokenRepository
    ) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationTokenDTOMapper = authenticationTokenDTOMapper;
        this.tokenRepository = tokenRepository;
    }

    /**
     *
     * @param form UserRegistrationRequest
     */
    public void userRegistration(UserRegistrationRequest form) {
        User user = User.builder()
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .email(form.getEmail())
                .role(Role.USER)
                .password(passwordEncoder.encode(form.getPassword()))
                .pin(passwordEncoder.encode(form.getPin()))
                .build();
        userRepository.save(user);
    }

    /**
     * Login user via pin and return user's token.
     * @param form UserLoginRequest
     * @return User's authentication token.
     */
    public AuthenticationTokenDTO userLoginViaPin(UserLoginRequest form) {
        User user = authenticateUserViaPin(form);
        String jwt = jwtService.generateToken(user);
        createUserToken(user,jwt);
        return authenticationTokenDTOMapper.apply(jwt,user.getRole());
    }

    /**
     * Generate a new user token.
     * @param user user entity
     * @param jwt token
     */
    private void createUserToken(User user, String jwt) {
        Token token = Token.builder()
                .user(user)
                .token(jwt)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    /**
     * Authentication user via pin.
     * @param form UserLoginRequest
     * @return user entity
     */
    private User authenticateUserViaPin(UserLoginRequest form) {
        User user = getUser(form.getEmail());
        if(!passwordEncoder.matches(form.getPin().toString(), user.getPin()))
            throw new CustomValidationException("Pin incorrect");
        return user;
    }

    /**
     * Returning user entity found by email.
     * @param email user's email
     * @return user entity
     */
    private User getUser(String email){
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()) throw new CustomValidationException("Login incorrect");
        return user.get();
    }

//    @Scheduled(fixedDelay = 1000)
//    public void test(){
//        System.out.println("TEST");
//    }
}
