package com.cdw.springenablement.helper_App.services;
import com.cdw.springenablement.helper_App.client.models.AuthenticationRequest;
import com.cdw.springenablement.helper_App.client.models.AuthenticationResponse;
import com.cdw.springenablement.helper_App.entity.Users;
import com.cdw.springenablement.helper_App.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
@Service
@lombok.RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        try {
            Users user = userRepository.findByEmail(authenticationRequest.getEmail())
                    .orElseThrow(() -> new NoSuchElementException("User not found"));
            if(user.getApproved().equals("approved")){
            String plainPassword = authenticationRequest.getPassword();
            String hashedPassword = user.getPassword();
            if (passwordEncoder.matches(plainPassword, hashedPassword)) {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
                );

                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();


                user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

                var jwtAccessToken = jwtService.generateToken(user, authorities);
                var jwtRefreshToken = jwtService.generateRefreshToken(user, authorities);
                AuthenticationResponse authenticationResponse = new AuthenticationResponse();
                authenticationResponse.setEmail(user.getEmail());
                authenticationResponse.setAccessToken(jwtAccessToken);
                authenticationResponse.setPassword(hashedPassword);
                return authenticationResponse;
            }
            else{
                throw new Exception("User is not yet approved by admin");
            }
            } else {

                throw new BadCredentialsException("Invalid credentials");

            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            AuthenticationResponse authenticationResponse = new AuthenticationResponse();
            authenticationResponse.setEmail("NO RESPONSE");
            authenticationResponse.setAccessToken("sj");

        }
    }
}
