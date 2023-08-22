package com.sirius.springenablement.demo.services;

import com.sirius.springenablement.demo.entity.Rooms;
import com.sirius.springenablement.demo.entity.TimeSlot;
import org.springframework.http.ResponseEntity;

import java.time.LocalTime;
import java.util.List;

public interface MeetingService {
    public List<Rooms> getAvailableRoomsNow(String date, LocalTime start_time, LocalTime end_time, int requiredCapacity) ;

    String bookMeeting(TimeSlot bookingRequest, int employeeId, String bookingType, Integer teamId, String roomName, List<Integer> collaborators);

    ResponseEntity<String> deleteMeeting(int timeSlotId);


    String updateMeeting(int timeSlotId, TimeSlot bookingRequest, String roomName, List<Integer> addedEmployees,List<Integer> removedEmployees);
}
