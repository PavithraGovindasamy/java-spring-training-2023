package com.cdw.springenablement.helperapp.Controller;

import com.cdw.springenablement.helperapp.client.models.HelperAppointmentDto;
import com.cdw.springenablement.helperapp.client.models.TimeSlotDto;
import com.cdw.springenablement.helperapp.client.models.TimeSlotDtos;
import com.cdw.springenablement.helperapp.controller.HelperController;
import com.cdw.springenablement.helperapp.services.interfaces.HelperService;
import com.cdw.springenablement.helperapp.services.interfaces.UserService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HelperControllerTest {

    @InjectMocks
    private HelperController helperController;

    @Mock
    private HelperService helperService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private MockMvc mockMvc;


    @Test
    public void testGetHelperAppointments(){
        Long helperId=1L;
        List<HelperAppointmentDto> appointmentDtos=new ArrayList<>();
        when(helperService.getAppointment()).thenReturn(appointmentDtos);
        ResponseEntity<List<HelperAppointmentDto>> helperAppointmentDtos= helperController.getHelperAppointments();
        assertEquals(appointmentDtos,helperAppointmentDtos.getBody());
    }


    @Test
    public void testAvailableTechnicians() {
        List<TimeSlotDto> timeSlotDtos = new ArrayList<>();
        Long id=1L;
        LocalDate date= LocalDate.parse("2023-09-09");
        when(helperService.getAvailableTechnicians(date,id)).thenReturn(timeSlotDtos);
        ResponseEntity<List<TimeSlotDto>> listResponseEntity =
                helperController.getAvailableTechnicians(date,id);
    }


    @Test
    public void  testAllTimeSlots(){
        List<TimeSlotDtos> timeSlotDtos=new ArrayList<>();
        Long offset=1L;
        when(helperService.getAllTimeSlots(offset,offset)).thenReturn(timeSlotDtos);
        ResponseEntity<List<TimeSlotDtos>> timeSlotDtoss=helperController.getAllTimeSlots(offset,offset);
        assertEquals(timeSlotDtos,timeSlotDtoss.getBody());
    }



}
