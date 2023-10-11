package com.cdw.springenablement.helper_App.services;
import com.cdw.springenablement.helper_App.client.models.AuthenticationRequest;
import com.cdw.springenablement.helper_App.client.models.AuthenticationResponse;
import com.cdw.springenablement.helper_App.constants.ErrorConstants;
import com.cdw.springenablement.helper_App.constants.SuceessConstants;
import com.cdw.springenablement.helper_App.entity.Users;
import com.cdw.springenablement.helper_App.exception.HelperAppException;
import com.cdw.springenablement.helper_App.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
                    .orElseThrow(() -> new NoSuchElementException(ErrorConstants.USER_NOT_FOUND_ERROR));

            if (SuceessConstants.STATUS_REGISTERED.equals(user.getApproved())) {
                throw new HelperAppException(ErrorConstants.USER_NOT_APPROVED_MESSAGE);
            }
            else if(SuceessConstants.STATUS_REJECTED.equals(user.getApproved())){
                throw new HelperAppException(ErrorConstants.USER_REJECTED_MESSAGE);
            }
            else {
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest.getPassword()));
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    System.out.println("roles"+user.getRoles());
                    user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
                    var jwtAccessToken = jwtService.generateToken(user, authorities);
                    authenticationResponse.setEmail(user.getEmail());
                    authenticationResponse.setAccessToken(jwtAccessToken);
                    authenticationResponse.setMessage(SuceessConstants.AUTHENTICATION_SUCCESSFULL_MESSSAGE);
               }
            }
         catch (RuntimeException e) {
            authenticationResponse.setMessage(e.getMessage());
        } catch (Exception e) {
            authenticationResponse.setMessage("Authentication failed: " + e.getMessage());
        }

        return authenticationResponse;
    }
}