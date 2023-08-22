package com.sirius.springenablement.demo.service;

import com.sirius.springenablement.demo.DemoApplication;
import com.sirius.springenablement.demo.DemoApplicationTests;
import com.sirius.springenablement.demo.entity.TimeSlot;
import com.sirius.springenablement.demo.repository.TimeSlotRepository;
import com.sirius.springenablement.demo.services.MeetingService;
import com.sirius.springenablement.demo.services.MeetingServiceImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(MockitoExtension.class)
class MeetingServiceImplTest {

    @Mock
    private TimeSlotRepository timeSlotRepository;


    @InjectMocks
    private MeetingServiceImpl meetingServiceImpl;

    @Test
    void testDeleteMeeting() {
        int timeSlotId = 1;
        TimeSlot timeSlot = new TimeSlot();
        LocalTime currentTime=LocalTime.now();
        LocalTime newTime=(currentTime.minusMinutes(40));
        timeSlot.setStart_time(newTime);
        System.out.println(currentTime+"ndw"+newTime);
        when(timeSlotRepository.findById(timeSlotId)).thenReturn(Optional.of(timeSlot));
        ResponseEntity<String> result = meetingServiceImpl.deleteMeeting(timeSlotId);
        assertEquals("Current time is not before 30 minutes before start time.", result);
    }





    @Test
    void updateMeeting() {
        int timeslotId=1;
    }
}