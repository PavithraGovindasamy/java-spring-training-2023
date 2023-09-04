package com.sirius.springenablement.meetingScheduler.controller;
import com.sirius.springenablement.meetingScheduler.dto.AvailableRoomsResponseDto;
import com.sirius.springenablement.meetingScheduler.dto.MeetingBookingResponseDto;
import com.sirius.springenablement.meetingScheduler.entity.TimeSlot;
import com.sirius.springenablement.meetingScheduler.repository.TimeSlotRepository;
import com.sirius.springenablement.meetingScheduler.req.TimeSlotRequest;
import com.sirius.springenablement.meetingScheduler.req.UpdateMeetingRequest;
import com.sirius.springenablement.meetingScheduler.services.interfaces.MeetingService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller class for meeting-related operations
 * @author pavithra
 */
@RestController
@RequestMapping("/meeting")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    /**
     * Method which returns the available rooms
     *
     * @param bookingRequest
     * @param requiredCapacity
     * @return
     */
    @GetMapping(path = "/availableRooms", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AvailableRoomsResponseDto> getAvailableRooms(@RequestBody TimeSlot bookingRequest, @RequestParam int requiredCapacity) {

            ResponseEntity<AvailableRoomsResponseDto> availableRooms = meetingService.getAvailableRoomsNow(
                    bookingRequest.getDate(),
                    bookingRequest.getStart_time(),
                    bookingRequest.getEnd_time(),
                    requiredCapacity
            );
            return availableRooms;

    }


    /**
     * Method which books a meeting for an employee
     *
     * @param bookingRequest
     * @return
     */
    @PostMapping("/bookMeeting")
    @Transactional
    public ResponseEntity<MeetingBookingResponseDto> bookMeeting(@RequestBody TimeSlotRequest bookingRequest) {
        try {
            MeetingBookingResponseDto result = meetingService.bookMeeting(
                    bookingRequest.getTimeSlot(),
                    bookingRequest.getEmployeeId(),
                    bookingRequest.getBookingType(),
                    bookingRequest.getTeamId(),
                    bookingRequest.getRoomName(),
                    bookingRequest.getCollaborators()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            e.printStackTrace();
            MeetingBookingResponseDto responseDto = new MeetingBookingResponseDto();
            responseDto.setSuccess(false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
        }
    }

    /**
     * Method for deleting a meeting
     *
     * @param timeSlotId
     * @return
     */
    @DeleteMapping("/{timeSlotId}")
    public ResponseEntity<MeetingBookingResponseDto> deleteMeeting(@PathVariable int timeSlotId) {
        try {
            MeetingBookingResponseDto result = meetingService.deleteMeeting(timeSlotId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();

            MeetingBookingResponseDto responseDto = new MeetingBookingResponseDto();
            responseDto.setSuccess(false);
            responseDto.setMessage("Failed to delete meeting");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }

    /**
     * Controller for updating a meeting
     */


    @PutMapping("/")
    public ResponseEntity<MeetingBookingResponseDto> updateMeeting(
            @RequestBody UpdateMeetingRequest request
    ) {
        try {
            MeetingBookingResponseDto responseDto = meetingService.updateMeeting(request);

            if (responseDto.isSuccess()) {
                return ResponseEntity.ok(responseDto);
            } else {
                return ResponseEntity.badRequest().body(responseDto);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            MeetingBookingResponseDto responseDto = new MeetingBookingResponseDto();
            responseDto.setSuccess(false);
            responseDto.setMessage("Failed to update meeting");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }

    }



}
