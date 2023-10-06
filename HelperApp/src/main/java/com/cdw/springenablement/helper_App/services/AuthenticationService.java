package com.cdw.springenablement.helper_App.services;
import com.cdw.springenablement.helper_App.client.models.AuthenticationRequest;
import com.cdw.springenablement.helper_App.client.models.AuthenticationResponse;
import com.cdw.springenablement.helper_App.entity.Users;
import com.cdw.springenablement.helper_App.exception.HelperAppException;
import com.cdw.springenablement.helper_App.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Services which genrates token for the user on validating the credentials
 */
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

    /**
     * Method which checks the user details and provides token
     * @param authenticationRequest
     * @return response where the token,details of the user
     */
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();

        try {
            Users user = userRepository.findByEmail(authenticationRequest.getEmail())
                    .orElseThrow(() -> new NoSuchElementException("User not found"));

            if ("registered".equals(user.getApproved())) {
                throw new HelperAppException("User is not yet approved by admin");
            } else {
                String plainPassword = authenticationRequest.getPassword();
                String hashedPassword = user.getPassword();
                if (passwordEncoder.matches(plainPassword, hashedPassword)) {
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
                    );
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

                    var jwtAccessToken = jwtService.generateToken(user, authorities);
                    authenticationResponse.setEmail(user.getEmail());
                    authenticationResponse.setAccessToken(jwtAccessToken);
                    authenticationResponse.setMessage("Authentication successful");
                } else {
                    throw new HelperAppException("Invalid credentials");
                }
            }

        } catch (RuntimeException e) {
            authenticationResponse.setMessage(e.getMessage());
        } catch (Exception e) {
            authenticationResponse.setMessage("Authentication failed: " + e.getMessage());
        }

        return authenticationResponse;
    }
}