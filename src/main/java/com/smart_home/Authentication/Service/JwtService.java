package com.smart_home.Authentication.Service;

import com.smart_home.Authentication.Model.Token;
import com.smart_home.Authentication.Model.User;
import com.smart_home.Authentication.Repository.TokenRepository;
import com.smart_home.Authentication.Repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    public JwtService(UserRepository userRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    /**
     *
     * @param token User authentication token.
     * @return Username (in this case username = email)
     */
    public String extractUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }

    /**
     *
     * @param request HttpServletRequest entity.
     * @return User entity.
     */
    public User extractUser(HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7);
        Token token1 = tokenRepository.findByToken(token).orElseThrow();
        return token1.getUser();
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     *
     * @param userDetails UserDetails entity.
     * @return Authentication token.
     */
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }

    /**
     *
     * @param extraClaims Empty HashMap.
     * @param userDetails UserDetails entity.
     * @return Jwts entity.
     */
    public String generateToken(Map<String,Object> extraClaims, UserDetails userDetails){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ 1000*60*60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     *
     * @param token User's authentication token.
     * @param userDetails UserDetails entity.
     * @return Flag checking if the user's token is active.
     */
    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private static final String SECRET_KEY = "586E3272357538782F413F4428472B4B6250655368566D597133733676397924";

    /**
     *
     * @param token User's authentication token.
     * @return Flag checking if the user's token is expired.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     *
     * @param token User's authentication token.
     * @return Date of token expiration.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
