package com.cdw.springenablement.helper_App.Controller;

import com.cdw.springenablement.helper_App.client.models.*;
import com.cdw.springenablement.helper_App.controller.HelperController;
import com.cdw.springenablement.helper_App.controller.UserController;
import com.cdw.springenablement.helper_App.services.AuthenticationService;
import com.cdw.springenablement.helper_App.services.TokenBlacklistService;
import com.cdw.springenablement.helper_App.services.interfaces.UserService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import javax.naming.AuthenticationException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {


    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;
    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private HelperController helperController;


    @Mock
    private TokenBlacklistService tokenBlacklistService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private MockMvc mockMvc;



    @Test
    public void testRegisterUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setEmail("pavithra@gmail.com");
        userDto.setDateOfBirth(LocalDate.parse("2023-09-09"));
        userDto.setGender("FEMALE");
        userDto.setRole(Collections.singletonList("role_resident"));
        userDto.setFirstName("pavi");
        userDto.setLastName("g");
        userDto.setPassword("js");

        int userId = 1;
        when(userService.registerUser(userDto)).thenReturn(userId);
        doNothing().when(userService).updateHelperSpecialization(userId, userDto.getSpecialisation());

        ResponseEntity<ApiResponseDto> responseEntity = userController.registerUser(userDto);
        ApiResponseDto expectedResponse = new ApiResponseDto()
                .message("User registered successfully. User ID: " + userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse.getMessage(), responseEntity.getBody().getMessage());


    }


    @Test
    public void testSuccessfulLogin() throws AuthenticationException {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("test@gmail.com");
        request.setPassword("sj");

        AuthenticationResponse expectedResponse = new AuthenticationResponse()
                       .accessToken("gn")
                        .email("aj");
        when(authenticationService.authenticate(request)).thenReturn(expectedResponse);

        ResponseEntity<AuthenticationResponse> responseEntity = userController.loginUser(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }





    @Test
    public void testbookTechnicianSlot() throws Exception {
        BookingTechnicianDto bookingTechnicianDto=new BookingTechnicianDto();
        bookingTechnicianDto.setEmail("pavo@gmail.com");
        bookingTechnicianDto.setGender("FEMALE");
        bookingTechnicianDto.setUserId(1);
        bookingTechnicianDto.setHelperId(2);
        doNothing().when(userService).bookTechnician(bookingTechnicianDto);
        ApiResponseDto responseDto=new ApiResponseDto()
                .message("Booked Slot Successfully");
        ResponseEntity<ApiResponseDto> responseEntity = userController.bookTechnicianSlot(bookingTechnicianDto);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseDto.getMessage(), responseEntity.getBody().getMessage());

    }

    @Test
    public void logoutUser(){
        LogoutUserRequest userRequest=new LogoutUserRequest();
              userRequest.setToken("valid-token");
        String token=userRequest.getToken();
        doNothing().when(tokenBlacklistService).addToBlacklist(token);
        ApiResponseDto responseDto=new ApiResponseDto()
                .message("Logged out Successfully");
        ResponseEntity<ApiResponseDto> responseEntity = userController.logoutUser(userRequest);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseDto.getMessage(), responseEntity.getBody().getMessage());
    }



//    @Test
//    public void testAvailableTechnicians() throws Exception {
//        LocalDate date = LocalDate.of(2023, 3, 2);
//        String profession = "plumber";
//        String startTime = "12:00";
//        String endTime = "13:00";
//        List<TimeSlotDto> timeSlotDtos = new ArrayList<>();
//
//        when(userService.getAvailableTechnicians(eq(date), eq(profession), eq(startTime), eq(endTime)))
//                .thenReturn(timeSlotDtos);
//
//        ResponseEntity<List<TimeSlotDto>> listResponseEntity =
//                userController.getAvailableTechnicians(date, profession, startTime, endTime);
//
//        assertSame(timeSlotDtos, listResponseEntity.getBody());
//    }


    @Test
    public void testGetHelperAppointments(){
        int helperId=1;
        List<HelperAppointmentDto> appointmentDtos=new ArrayList<>();
        when(userService.getAppointment((long) helperId)).thenReturn(appointmentDtos);
        ResponseEntity<List<HelperAppointmentDto>> helperAppointmentDtos= helperController.getHelperAppointments((long) helperId);
        assertEquals(appointmentDtos,helperAppointmentDtos.getBody());



    }


}


