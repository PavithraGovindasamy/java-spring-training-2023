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

/**
 * controller that implements usersApi
 * @Author pavithra
 */
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

    /**
     *
     * @param userDto Registering a user (required)
     * @return Response where the user is registered
     */
    @Override
    public ResponseEntity<ApiResponseDto> registerUser(UserDto userDto) {
        int id = userService.registerUser(userDto);
        userService.updateHelperSpecialization(id, userDto.getSpecialisation());
        ApiResponseDto response = new ApiResponseDto()
                .message("User registered successfully." + " User ID: " + id)
                .statusCode((long) HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    /**
     *
     * @param authenticationRequest Authenticate a user with email and password (required)
     * @return  response where response return the token
     */

    @Override
    public ResponseEntity<AuthenticationResponse> loginUser(AuthenticationRequest authenticationRequest) {
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequest);
        return ResponseEntity.ok(authenticationResponse);
    }

    /**
     *
     * Method which give the available technicians timeslot
     * @return response where list of timeslot is returned
     */
    @Override
    public ResponseEntity<List<TimeSlotDto>> getAvailableTechnicians() {
        List<TimeSlotDto> technicianDtos = userService.getAvailableTechnicians();
        return ResponseEntity.ok(technicianDtos);
    }

    /**
     *
     * @param bookingTechnicianDto Booking a slot with a technician (required)
     * @return response where the slot is booked or not
     */

    @Override
    public ResponseEntity<ApiResponseDto> bookTechnicianSlot(BookingTechnicianDto bookingTechnicianDto) {
        userService.bookTechnician(bookingTechnicianDto);
        ApiResponseDto response = new ApiResponseDto()
                .message("Booked Slot Successfully")
                .statusCode((long) HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    /**
     *
     * @param logoutUserRequest Logout a user by invalidating the token (required)
     * @return response where the slot is logged out or not
     */

    @Override
    public ResponseEntity<ApiResponseDto> logoutUser(LogoutUserRequest logoutUserRequest) {
        String token = logoutUserRequest.getToken();
        tokenBlacklistService.addToBlacklist(token);
        ApiResponseDto response = new ApiResponseDto()
                .message("Logged out Successfully")
                .statusCode((long) HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }
}
