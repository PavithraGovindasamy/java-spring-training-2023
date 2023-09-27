package com.sirius.springenablement.helper_App.services;
import com.sirius.springenablement.helper_App.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.sirius.springenablement.helper_App.entity.Users;
import com.sirius.springenablement.helper_App.repository.UserRepository;
import com.sirius.springenablement.helper_App.repository.RolesRepository;
import  java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.sirius.springenablement.helper_App.dto.UserDto;
import com.sirius.springenablement.helper_App.entity.Roles;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl  implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolesRepository rolesRepository;

    private final PasswordEncoder passwordEncoder;

    public void registerUser(UserDto userDto)  {


        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            try {
                throw new Exception("User with this email already exists.");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        Users user = new Users();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setGender(userDto.getGender());
        user.setDateOfBirth(userDto.getDateOfBirth());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        user.setApproved("registered");

        Set<Roles> roles = new HashSet<>();
        Roles defaultRole = rolesRepository.findByName("ROLE_USER");
        roles.add(defaultRole);

        user.setRoles(roles);
        userRepository.save(user);
    }





    public UserDetails loadUserByUsername(String username) throws org.springframework.security.core.userdetails.UsernameNotFoundException {
        Optional<Users> userOptional = userRepository.findByEmail(username);

        if (userOptional.isEmpty()) {
            throw new org.springframework.security.core.userdetails.UsernameNotFoundException("User not found with email: " + username);
        }

        Users user = userOptional.get();

        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.getPassword())
                .authorities(user.getRoles().toString())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }





    @Override
    public void addToUser(String username, String roleName) {
         Users users=userRepository.findByEmail(username).orElse(null);
         Roles roles=rolesRepository.findByName(roleName);
         users.getRoles().add(roles);

    }
}
