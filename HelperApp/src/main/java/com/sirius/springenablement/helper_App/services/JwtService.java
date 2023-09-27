package com.sirius.springenablement.helper_App.services;
import com.sirius.springenablement.helper_App.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import com.sirius.springenablement.helper_App.entity.Users;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.stream.Collectors;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.context.annotation.PropertySource;


@Component
@PropertySource("classpath:application.properties")
@Service
public class JwtService {


    private String secretKey="123";

    @Autowired
    private UserRepository userRepository;

 public String generateToken(Users users , Collection<SimpleGrantedAuthority> authorities){
     Algorithm algorithm=Algorithm.HMAC256(secretKey.getBytes());
     return com.auth0.jwt.JWT.create()
             .withSubject(users.getEmail())
             .withExpiresAt(new java.util.Date(System.currentTimeMillis()+50*60*1000))
             .withClaim("roles",authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
             .sign(algorithm);
 }

    public String generateRefreshToken(Users users , Collection<SimpleGrantedAuthority> authorities){
        Algorithm algorithm=Algorithm.HMAC256(secretKey.getBytes());
        return com.auth0.jwt.JWT.create()
                .withSubject(users.getEmail())
                .withExpiresAt(new java.util.Date(System.currentTimeMillis()+70*60*1000))
                .sign(algorithm);
    }
}