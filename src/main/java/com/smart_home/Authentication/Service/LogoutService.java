package com.smart_home.Authentication.Service;

import com.smart_home.Authentication.Model.Token;
import com.smart_home.Authentication.Repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
public class LogoutService implements LogoutHandler {
    private final TokenRepository tokenRepository;
    public LogoutService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    /**
     *
     * @param request HttpServletRequest entity.
     * @param response HttpServletResponse entity.
     * @param authentication Authentication entitny.
     * Logging out the user and setting all tokens to true
     */
    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }
        jwt = authHeader.substring(7);
        Token token = tokenRepository.findByToken(jwt).orElse(null);
        if (token != null){
            token.setExpired(true);
        }
        tokenRepository.save(token);
    }
}
