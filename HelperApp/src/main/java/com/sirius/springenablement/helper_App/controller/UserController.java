package com.sirius.springenablement.helper_App.controller;

import com.sirius.springenablement.helper_App.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sirius.springenablement.helper_App.dto.ApiResponseDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.sirius.springenablement.helper_App.dto.UserDto;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<ApiResponseDto> registerUser(@RequestBody UserDto userDto) {
        try {
            userService.registerUser(userDto);

            ApiResponseDto response = new ApiResponseDto("User registered successfully.", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponseDto response = new ApiResponseDto("Registration failed: " + e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


}
