package com.sirius.springenablement.ticket_booking.controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import com.sirius.springenablement.ticket_booking.dto.UserDto;
import  com.sirius.springenablement.ticket_booking.services.UserServiceImpl;
import org.springframework.http.HttpStatus;
import com.sirius.springenablement.ticket_booking.dto.ApiResponseDto;
import com.sirius.springenablement.ticket_booking.dto.*;
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private com.sirius.springenablement.ticket_booking.services.BookingService bookingService;

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


    @Autowired
    private com.sirius.springenablement.ticket_booking.services.JwtService jwtService;




        @PostMapping("/book")
        public ResponseEntity<BookingResponseDto> bookTicket(@RequestBody com.sirius.springenablement.ticket_booking.services.BookingRequestDto bookingRequestDto) {
            try {
                BookingResponseDto bookingResponseDto = bookingService.bookTicket(bookingRequestDto);
                return ResponseEntity.ok(bookingResponseDto);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }



}


