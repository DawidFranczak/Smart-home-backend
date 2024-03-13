package com.smart_home.Authentication.Repository;

import com.smart_home.Authentication.Model.User;
import com.smart_home.Authentication.Permission.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .firstName("Tester")
                .lastName("Tester2")
                .email("test@test.com")
                .password("password")
                .pin("1234")
                .role(Role.USER)
                .build();
        userRepository.save(user);

    }

    @Test
    void findByEmail() {
        Optional<User> user = userRepository.findByEmail("test@test.com");
        Optional<User> user2 = userRepository.findByEmail("test2@test.com");

        Assertions.assertThat(user.isPresent()).isTrue();
        Assertions.assertThat(user2.isPresent()).isFalse();
    }
}