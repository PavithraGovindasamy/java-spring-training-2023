package com.sirius.springenablement.ticket_booking.security;
import com.sirius.springenablement.ticket_booking.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWTVerifier;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.ArrayList;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import  java.util.Collection;
import com.sirius.springenablement.ticket_booking.dto.ErrorResponse;
@org.springframework.stereotype.Component
@lombok.RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private final UserRepository userRepository;

    private String secretKey="123";




    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(AUTHORIZATION);



        if (authorizationHeader != null  && authorizationHeader.startsWith("Bearer ")) {

         try{
             String token=authorizationHeader.substring("Bearer ".length());
             Algorithm algorithm= Algorithm.HMAC256(secretKey.getBytes());
             JWTVerifier verifier= com.auth0.jwt.JWT.require(algorithm).build();
             DecodedJWT decodeJWT=verifier.verify(token);
             String username=decodeJWT.getSubject();
             userRepository.findByEmail(username).orElseThrow(()->new Exception("Invalid token"));

             String[] roles=decodeJWT.getClaim("roles").asArray(String.class);
            Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();
             java.util.Arrays.stream(roles).forEach(role->
                     authorities.add(new SimpleGrantedAuthority(role)));
             UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(username,null,authorities);
             SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
             filterChain.doFilter(request,response);
         } catch (Exception e) {
            ErrorResponse errorResponse=new ErrorResponse(HttpStatus.FORBIDDEN,e.getMessage());
           response.setContentType(APPLICATION_JSON_VALUE);
           response.setStatus(errorResponse.getStatusCodeValue());
           new com.fasterxml.jackson.databind.ObjectMapper().writeValue(response.getOutputStream(),errorResponse);
         }
        }

else{
    filterChain.doFilter(request,response);
        }
    }

}
