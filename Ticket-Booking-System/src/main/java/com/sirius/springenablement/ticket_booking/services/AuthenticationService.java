package com.sirius.springenablement.ticket_booking.services;
import com.sirius.springenablement.ticket_booking.entity.Users;
import com.sirius.springenablement.ticket_booking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AuthenticationService {



    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    public String authenticateAndGenerateToken(String email, String password) {
        Users user = userRepository.findByEmail(email).orElse(null);

        if (user != null && user.getPassword().equals(password)) {
            return jwtService.generateToken(email);
        }

        return null;
    }
    }

