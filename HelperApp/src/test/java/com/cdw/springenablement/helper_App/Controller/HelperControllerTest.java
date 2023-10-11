package com.cdw.springenablement.helper_App.Controller;

import com.cdw.springenablement.helper_App.client.models.HelperAppointmentDto;
import com.cdw.springenablement.helper_App.controller.HelperController;
import com.cdw.springenablement.helper_App.services.interfaces.UserService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HelperControllerTest {

    @InjectMocks
    private HelperController helperController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private MockMvc mockMvc;


    @Test
    public void testGetHelperAppointments(){
        Long helperId=1L;
        List<HelperAppointmentDto> appointmentDtos=new ArrayList<>();
        when(userService.getAppointment( helperId)).thenReturn(appointmentDtos);
        ResponseEntity<List<HelperAppointmentDto>> helperAppointmentDtos= helperController.getHelperAppointments((long) helperId);
        assertEquals(appointmentDtos,helperAppointmentDtos.getBody());



    }

}
