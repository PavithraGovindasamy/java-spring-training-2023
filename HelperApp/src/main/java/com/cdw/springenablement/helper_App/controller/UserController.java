package com.cdw.springenablement.helper_App.controller;
import com.cdw.springenablement.helper_App.client.api.UsersApi;
import com.cdw.springenablement.helper_App.client.models.*;
import com.cdw.springenablement.helper_App.repository.UserRepository;
import com.cdw.springenablement.helper_App.services.AuthenticationService;
import com.cdw.springenablement.helper_App.services.JwtService;
import com.cdw.springenablement.helper_App.services.TokenBlacklistService;
import com.cdw.springenablement.helper_App.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;
import com.cdw.springenablement.helper_App.entity.Users;

@RestController
public class UserController implements UsersApi {

    @Autowired
    public UserService userService;
    @Autowired
    private TokenBlacklistService tokenBlacklistService ;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<ApiResponseDto> registerUser(UserDto userDto) {
        try {


            int id=userService.registerUser(userDto);
            userService.updateHelperSpecialization(id, userDto.getSpecialisation());
            System.out.println("jsj"+ userDto.getSpecialisation());

            ApiResponseDto response = new ApiResponseDto()
                    .message("User registered successfully."+" User ID: "+id)
                    .statusCode((long) HttpStatus.OK.value());
           return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();

            ApiResponseDto response = new ApiResponseDto()
                    .message("Registration failed: " + e.getMessage())
                    .statusCode((long) HttpStatus.BAD_REQUEST.value());
            System.out.println('s'+response.getStatusCode());
            return ResponseEntity.ok(response);
        }
    }

    @Override
    public ResponseEntity<AuthenticationResponse> loginUser(AuthenticationRequest authenticationRequest) {
      AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequest);

        if (authenticationResponse != null) {
            return ResponseEntity.ok(authenticationResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }



    @Override
    public ResponseEntity<List<TimeSlotDto>> getAvailableTechnicians(LocalDate date, String profession, String startTime, String endTime) {
        List<TimeSlotDto>  technicianDtos= null;
        try {
            technicianDtos = userService.getAvailableTechnicians(date, profession, startTime, endTime);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(technicianDtos);
    }


    @Override
    public ResponseEntity<ApiResponseDto> bookTechnicianSlot(BookingTechnicianDto bookingTechnicianDto) {
    try{

        userService.bookTechnician(bookingTechnicianDto);
        ApiResponseDto response = new ApiResponseDto()
                .message("Booked Slot Successfully")
                .statusCode((long) HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }catch (Exception e) {
        ApiResponseDto response = new ApiResponseDto()
                .message("Registration failed: " + e.getMessage())
                .statusCode((long) HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.ok(response);
    }
    }

    @Override
    public ResponseEntity<ApiResponseDto> logoutUser(LogoutUserRequest logoutUserRequest) {
        String token=logoutUserRequest.getToken();
         tokenBlacklistService.addToBlacklist(token);
        ApiResponseDto response = new ApiResponseDto()
                .message("Logged out Successfully")
                .statusCode((long) HttpStatus.OK.value());
        return ResponseEntity.ok(response);    }
}
