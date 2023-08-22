package com.sirius.springenablement.demo.controller;

import com.sirius.springenablement.demo.entity.TimeSlot;
import com.sirius.springenablement.demo.services.MeetingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MeetingControllerTest {

    @InjectMocks
    private MeetingController meetingController;

    @Mock
    private MeetingService meetingService;

//
//    @Test
//    public void testDeleteMeeting() {
//        int timeSlotId = 123;
//        when(meetingService.deleteMeeting(timeSlotId))
//                .thenReturn("Meeting deleted");
//        ResponseEntity<String> response = meetingController.deleteMeeting(timeSlotId);
//        assertEquals("Meeting deleted", response.getBody());
//    }

    @Test
    public void testBookMeeting() {
        TimeSlot timeSlot = new TimeSlot();
        int employeeId = 1;
        String bookingType = "TEAM";
        Integer teamId = 4;
        String roomName = "Meeting Room A";
        List<Integer> collaborators = List.of(9, 7);
        when(meetingService.bookMeeting(timeSlot, employeeId, bookingType, teamId, roomName, collaborators))
                .thenReturn("Meeting booked");


        ResponseEntity<String> response = meetingController.bookMeeting(
                timeSlot, employeeId, bookingType, teamId, roomName, collaborators
        );

        assertEquals("Meeting booked", response.getBody());
    }

    @Test
    public void testUpdateMeeting(){
        TimeSlot timeSlot = new TimeSlot();
        int timeSlotId=2;
        Integer teamId = 4;
        String roomName = "Training Room 1";
        List<Integer> addedEmployees=List.of(1,2);
        List<Integer> removedEmployees=List.of(3);
        when( meetingService.updateMeeting(timeSlotId, timeSlot, roomName, addedEmployees, removedEmployees))
                .thenReturn("Meeting updated");

        ResponseEntity<String> response = meetingController.updateMeeting(
                timeSlotId, timeSlot, roomName, addedEmployees, removedEmployees
        );
        assertEquals("Meeting updated", response.getBody());
    }

//    @Test
//    public void testAvailableRooms() {
//        String date = "12-9-2002";
//        LocalTime startTime = LocalTime.now();
//        LocalTime endTime = LocalTime.now().plusMinutes(10);
//        int requiredCapacity = 12;
//        TimeSlot timeslot=new TimeSlot();
//        when(meetingService.getAvailableRoomsNow(date, startTime, endTime, requiredCapacity)).thenReturn("Me");
//
//        ResponseEntity<String> response = meetingController.getAvailableRooms(
//               timeslot, requiredCapacity
//        );
//
//        assertEquals("Room Available", response.getBody());
//    }






}
