package com.sirius.springenablement.meetingScheduler.services;

import com.sirius.springenablement.meetingScheduler.dto.AvailableRoomsResponseDto;
import com.sirius.springenablement.meetingScheduler.dto.MeetingBookingResponseDto;
import com.sirius.springenablement.meetingScheduler.entity.TimeSlot;
import com.sirius.springenablement.meetingScheduler.req.UpdateMeetingRequest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MeetingService {
    public ResponseEntity<AvailableRoomsResponseDto> getAvailableRoomsNow(LocalDate date, LocalTime start_time, LocalTime end_time, int requiredCapacity);

    MeetingBookingResponseDto bookMeeting(TimeSlot bookingRequest, int employeeId, String bookingType, Integer teamId, String roomName, List<Integer> collaborators);

    MeetingBookingResponseDto deleteMeeting(int timeSlotId);


    public MeetingBookingResponseDto updateMeeting(UpdateMeetingRequest request);
}
