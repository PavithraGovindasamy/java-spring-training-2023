package com.sirius.springenablement.ticket_booking.services;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import com.sirius.springenablement.ticket_booking.entity.Users;
import java.security.Key;
import java.util.Date;
import com.sirius.springenablement.ticket_booking.entity.Roles;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.context.annotation.PropertySource;
import lombok.Value;
@Component
@PropertySource("classpath:application.properties")
@Service
public class JwtService {


    private String secretKey="123";

 @org.springframework.beans.factory.annotation.Autowired
    private com.sirius.springenablement.ticket_booking.repository.UserRepository userRepository;

 public String generateToken(Users users , Collection<SimpleGrantedAuthority> authorities){
     Algorithm algorithm=Algorithm.HMAC256(secretKey.getBytes());
     return com.auth0.jwt.JWT.create()
             .withSubject(users.getEmail())
             .withExpiresAt(new java.util.Date(System.currentTimeMillis()+50*60*1000))
             .withClaim("roles",authorities.stream().map(org.springframework.security.core.GrantedAuthority::getAuthority).collect(java.util.stream.Collectors.toList()))
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