package com.sirius.springenablement.ticket_booking.auth;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.sirius.springenablement.ticket_booking.dto.LoginRequest;
import com.sirius.springenablement.ticket_booking.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import com.sirius.springenablement.ticket_booking.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
@RestController
@RequestMapping("/api")
@lombok.RequiredArgsConstructor
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginRequest loginRequest) {
        String providedEmail = loginRequest.getEmail();
        String providedPassword = loginRequest.getPassword();

        String jwtToken = authenticationService.authenticateAndGenerateToken(providedEmail, providedPassword);

        if (jwtToken != null) {
            return ResponseEntity.ok(jwtToken);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }
}
