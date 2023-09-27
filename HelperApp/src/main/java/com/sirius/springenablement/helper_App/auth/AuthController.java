package com.sirius.springenablement.helper_App.auth;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.sirius.springenablement.helper_App.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import com.sirius.springenablement.helper_App.services.AuthenticationService;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
@lombok.RequiredArgsConstructor
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) {
      return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }
}
