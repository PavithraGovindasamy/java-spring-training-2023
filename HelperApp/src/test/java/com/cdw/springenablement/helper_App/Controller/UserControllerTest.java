package com.cdw.springenablement.helper_App.Controller;

import com.cdw.springenablement.helper_App.client.models.*;
import com.cdw.springenablement.helper_App.constants.SuceessConstants;
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

import java.util.ArrayList;
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
    public void testbookTechnicianSlot() throws Exception {
        BookingTechnicianDto bookingTechnicianDto=new BookingTechnicianDto();
        bookingTechnicianDto.setEmail("pavo@gmail.com");
        bookingTechnicianDto.setGender("FEMALE");
        bookingTechnicianDto.setHelperId(2L);
        doNothing().when(userService).bookTechnician(bookingTechnicianDto);
        ApiResponseDto responseDto=new ApiResponseDto()
                .message(SuceessConstants.BOOKED_SUCCESSFULLY_MESSAGE);
        ResponseEntity<ApiResponseDto> responseEntity = userController.bookTechnicianSlot(bookingTechnicianDto);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseDto.getMessage(), responseEntity.getBody().getMessage());

    }




    @Test
    public void testAvailableTechnicians() throws Exception {
        List<TimeSlotDto> timeSlotDtos = new ArrayList<>();
        when(userService.getAvailableTechnicians()).thenReturn(timeSlotDtos);
        ResponseEntity<List<TimeSlotDto>> listResponseEntity =
                userController.getAvailableTechnicians();

        assertSame(timeSlotDtos, listResponseEntity.getBody());
    }




}


