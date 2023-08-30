package com.sirius.springenablement.meetingScheduler.controller;

import com.sirius.springenablement.meetingScheduler.dto.AvailableRoomsResponseDto;
import com.sirius.springenablement.meetingScheduler.dto.MeetingBookingResponseDto;
import com.sirius.springenablement.meetingScheduler.entity.TimeSlot;
import com.sirius.springenablement.meetingScheduler.req.TimeSlotRequest;
import com.sirius.springenablement.meetingScheduler.services.MeetingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.ExpectedCount.times;

@ExtendWith(MockitoExtension.class)
public class MeetingControllerTest {

    @InjectMocks
    private MeetingController meetingController;

    @Mock
    private MeetingService meetingService;

    @Test
    public void testDeleteMeeting() {
        int timeSlotId = 123;
        MeetingBookingResponseDto mockResponseDto = new MeetingBookingResponseDto();
        mockResponseDto.setSuccess(true);
        mockResponseDto.setMessage("Meeting deleted");
        when(meetingService.deleteMeeting(timeSlotId))
                .thenReturn(mockResponseDto);

        ResponseEntity<MeetingBookingResponseDto> response = meetingController.deleteMeeting(timeSlotId);
        MeetingBookingResponseDto responseBody = response.getBody();
        assertNotNull(responseBody);
        assertTrue(responseBody.isSuccess());
        assertEquals("Meeting deleted", responseBody.getMessage());
    }


    @Test
    public void testBookMeeting() {

        LocalDate date = LocalDate.of(2012, 7, 9);
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(12, 0);
        String description="new event";
        TimeSlot mockTimeSlot = new TimeSlot(date, startTime, endTime,description);

        TimeSlotRequest mockRequest = new TimeSlotRequest();
        mockRequest.setTimeSlot(mockTimeSlot);


        MeetingBookingResponseDto mockResponseDto = new MeetingBookingResponseDto();
        mockResponseDto.setSuccess(true);

        when(meetingService.bookMeeting(
                mockRequest.getTimeSlot(),
                mockRequest.getEmployeeId(),
                mockRequest.getBookingType(),
                mockRequest.getTeamId(),
                mockRequest.getRoomName(),
                mockRequest.getCollaborators()
        )).thenReturn(mockResponseDto);

        ResponseEntity<MeetingBookingResponseDto> response = meetingController.bookMeeting(mockRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockResponseDto, response.getBody());
    }








    @Test
    public void testAvailableRooms()  {
    LocalDate date = LocalDate.of(2023, 8, 28);
    LocalTime startTime = LocalTime.of(10, 0);
    LocalTime endTime = LocalTime.of(10, 10);
    String description = "Test Description";
    TimeSlot bookingRequest = new TimeSlot(date, startTime, endTime, description);


    AvailableRoomsResponseDto mockResponseDto = new AvailableRoomsResponseDto();
        mockResponseDto.setSuccess(true);
        mockResponseDto.setMessage("Room available");
    ResponseEntity<AvailableRoomsResponseDto> mockResponseEntity =
            new ResponseEntity<>(mockResponseDto, HttpStatus.OK);


        int requiredCapacity=12;
        when(meetingService.getAvailableRoomsNow(
            bookingRequest.getDate(),
                bookingRequest.getStart_time(),
                        bookingRequest.getEnd_time(),
    requiredCapacity))
            .thenReturn(mockResponseEntity);

    ResponseEntity<AvailableRoomsResponseDto> response = meetingController.getAvailableRooms(
            bookingRequest, requiredCapacity);


    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(mockResponseDto, response.getBody());

}



}
