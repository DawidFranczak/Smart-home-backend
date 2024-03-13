package com.smart_home.Authentication.Repository;

import com.smart_home.Authentication.Model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Long> {
    @Query("""
            select t from Token t inner join User u on t.user.id = u.id where u.id = :id
            and (t.expired = false)
            """)
     List<Token> findAllValidTokensByUser(Long id);

     Optional<Token> findByToken(String token);
}
