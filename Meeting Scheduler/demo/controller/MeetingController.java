package com.sirius.springenablement.demo.controller;

import com.sirius.springenablement.demo.entity.Rooms;
import com.sirius.springenablement.demo.entity.TimeSlot;
import com.sirius.springenablement.demo.services.MeetingService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

/**
 * Controller class for meeting-related operations
 */
@RestController
@RequestMapping("/meeting")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    /**
     * Method which returns the available rooms
     *
     * @param bookingRequest
     * @param requiredCapacity
     * @return
     */
    @GetMapping("/availableRooms")
    public ResponseEntity<String> getAvailableRooms(@RequestBody TimeSlot bookingRequest, @RequestParam int requiredCapacity) {
        List<Rooms> availableRooms = meetingService.getAvailableRoomsNow(
                bookingRequest.getDate(),
                bookingRequest.getStart_time(),
                bookingRequest.getEnd_time(),
                requiredCapacity
        );
        return ResponseEntity.ok("okay " + availableRooms);
    }

    /**
     * Method which books a meeting for an employee
     *
     * @param bookingRequest
     * @param employeeId
     * @param bookingType
     * @param teamId
     * @param roomName
     * @param collaborators
     * @return
     */
    @PostMapping("/bookMeeting/{employeeId}/{bookingType}")
    @Transactional
    public ResponseEntity<String> bookMeeting(@RequestBody TimeSlot bookingRequest, @PathVariable int employeeId, @PathVariable String bookingType,
                                              @RequestParam(required = false) Integer teamId, @RequestParam String roomName,
                                              @RequestParam(required = false) List<Integer> collaborators) {
        String result = meetingService.bookMeeting(bookingRequest, employeeId, bookingType, teamId, roomName, collaborators);
        return ResponseEntity.ok(result);
    }

    /**
     * Method for deleting a meeting
     *
     * @param timeSlotId
     * @return
     */
    @DeleteMapping("/delete/{timeSlotId}")
    public ResponseEntity<String> deleteMeeting(@PathVariable int timeSlotId) {
        String result = String.valueOf(meetingService.deleteMeeting(timeSlotId));
        return ResponseEntity.ok(result);
    }

    /**
     * Method for updating a meeting
     *
     * @param timeSlotId
     * @param bookingRequest
     * @param roomName
     * @param addedEmployees
     * @param removedEmployees
     * @return
     */
    @PutMapping("/update/{timeSlotId}")
    public ResponseEntity<String> updateMeeting(
            @PathVariable int timeSlotId,
            @RequestBody TimeSlot bookingRequest,
            @RequestParam String roomName,
            @RequestParam(name = "addedEmployees") List<Integer> addedEmployees,
            @RequestParam(name = "removedEmployees") List<Integer> removedEmployees
    ) {
        String result = meetingService.updateMeeting(timeSlotId, bookingRequest, roomName, addedEmployees, removedEmployees);
        return ResponseEntity.ok(result);
    }


}
