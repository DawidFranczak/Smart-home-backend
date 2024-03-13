package com.smart_home.Authentication.Repository;

import com.smart_home.Authentication.Model.Token;
import com.smart_home.Authentication.Model.User;
import com.smart_home.Authentication.Permission.Role;
import com.smart_home.Authentication.Permission.TokenType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class TokenRepositoryTest {

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    UserRepository userRepository;


    @BeforeEach
    void setUp(){
        User user = User.builder()
                .firstName("Tester")
                .lastName("Tester2")
                .email("test@test.com")
                .password("password")
                .pin("1234")
                .role(Role.USER)
                .build();
        userRepository.save(user);

        Token token = Token.builder()
                .token("TestToken")
                .user(user)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .build();
        Token token2 = Token.builder()
                .token("TestToken2")
                .user(user)
                .tokenType(TokenType.BEARER)
                .expired(true)
                .build();
        List<Token> tokenList = List.of(token,token2);
        tokenRepository.saveAll(tokenList);

    }

    @Test
    void findAllValidTokensByUser() {
        User user = userRepository.findByEmail("test@test.com").get();
        List<Token> allValidTokensByUser = tokenRepository.findAllValidTokensByUser(user.getId());
        Assertions.assertEquals(1,allValidTokensByUser.size());

    }

    @Test
    void findByToken() {
        Optional<Token> testToken = tokenRepository.findByToken("TestToken");
        Optional<Token> testToken2 = tokenRepository.findByToken("TestToken3");

        assertTrue(testToken.isPresent());
        assertTrue(testToken2.isEmpty());
    }
}