package com.sirius.springenablement.helper_App.services;
import com.sirius.springenablement.helper_App.auth.AuthenticationRequest;
import com.sirius.springenablement.helper_App.entity.Users;
import com.sirius.springenablement.helper_App.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import com.sirius.springenablement.helper_App.auth.AuthenticationResponse;
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

    public ResponseEntity<?> authenticate(AuthenticationRequest authenticationRequest) {
        try {
            Users user = userRepository.findByEmail(authenticationRequest.getEmail())
                    .orElseThrow(() -> new NoSuchElementException("User not found"));
            String plainPassword = authenticationRequest.getPassword();
            String hashedPassword = user.getPassword();
            if (passwordEncoder.matches(plainPassword, hashedPassword)) {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
                );

                java.util.Collection<SimpleGrantedAuthority> authorities = new java.util.ArrayList<>();


                user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

                var jwtAccessToken = jwtService.generateToken(user, authorities);
                var jwtRefreshToken = jwtService.generateRefreshToken(user, authorities);

                return ResponseEntity.ok(AuthenticationResponse.builder()
                        .access_token(jwtAccessToken)
                        .refresh_token(jwtRefreshToken)
                                .email(user.getEmail())
                                .password(user.getPassword())
                        .build());
            }
            else
            {

                throw new BadCredentialsException("Invalid credentials");

            }
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(org.springframework.http.HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("INVALID CREDENTIALS");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }
}
