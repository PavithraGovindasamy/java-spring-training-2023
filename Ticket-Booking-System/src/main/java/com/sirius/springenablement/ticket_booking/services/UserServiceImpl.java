package com.sirius.springenablement.ticket_booking.services;
import org.springframework.beans.factory.annotation.Autowired;
import com.sirius.springenablement.ticket_booking.entity.Users;
import com.sirius.springenablement.ticket_booking.repository.UserRepository;
import  java.time.LocalDate;
import com.sirius.springenablement.ticket_booking.dto.UserDto;
import com.sirius.springenablement.ticket_booking.entity.Roles;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import  java.util.Optional;
@Service
public class UserServiceImpl  {
    @Autowired
    private UserRepository userRepository;

    public void registerUser(UserDto userDto) throws Exception {
        if (!isUserAbove18(userDto.getDateOfBirth())) {
            throw new Exception("User must be 18 years or older.");
        }


        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new Exception("User with this email already exists.");
        }

        Users user = new Users();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setGender(userDto.getGender());
        user.setDateOfBirth(userDto.getDateOfBirth());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(Roles.USER);
        userRepository.save(user);
    }



    private boolean isUserAbove18(LocalDate dateOfBirth) {
        LocalDate today = LocalDate.now();
        return java.time.Period.between(dateOfBirth, today).getYears() >= 18;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional<Users> userOptional = userRepository.findByEmail(username);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        Users user = userOptional.get();

        return User
                .withUsername(username)
                .password(user.getPassword())
                .authorities(user.getRole().toString())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }


}
