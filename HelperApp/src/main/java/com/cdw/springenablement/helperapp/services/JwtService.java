package com.cdw.springenablement.helperapp.services;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cdw.springenablement.helperapp.constants.SuceessConstants;
import com.cdw.springenablement.helperapp.entity.Users;
import com.cdw.springenablement.helperapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.context.annotation.PropertySource;

/**
 * Service which handles token generation
 * @Author pavithra
 */

@Component
@PropertySource("classpath:application.properties")
@Service
public class JwtService {
    private String secretKey="123";

    @Autowired
    private UserRepository userRepository;

    /**
     *
     * @param users
     * @param authorities
     * @return
     */
     public String generateToken(Users users , Collection<SimpleGrantedAuthority> authorities){
     Algorithm algorithm=Algorithm.HMAC256(secretKey.getBytes());
     return com.auth0.jwt.JWT.create()
             .withSubject(users.getEmail())
             .withExpiresAt(new java.util.Date(System.currentTimeMillis() + SuceessConstants.TIME_LIMIT))
             .withClaim("roles",authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
             .sign(algorithm);
     }
    /**
     * Extract the remaining time in minutes until the JWT token expires.
     */
    public long extractRemainingTime(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        Date expirationTime = decodedJWT.getExpiresAt();
        Date currentTime = new Date();
        long remainingMillis = expirationTime.getTime() - currentTime.getTime();
        return remainingMillis ;
    }


}