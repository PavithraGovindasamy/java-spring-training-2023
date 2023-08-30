package com.sirius.springenablement.meetingScheduler.service;

import com.sirius.springenablement.meetingScheduler.controller.MeetingController;
import com.sirius.springenablement.meetingScheduler.dto.AvailableRoomsResponseDto;
import com.sirius.springenablement.meetingScheduler.dto.MeetingBookingResponseDto;
import com.sirius.springenablement.meetingScheduler.entity.Employee;
import com.sirius.springenablement.meetingScheduler.entity.Rooms;
import com.sirius.springenablement.meetingScheduler.entity.Teams;
import com.sirius.springenablement.meetingScheduler.entity.TimeSlot;
import com.sirius.springenablement.meetingScheduler.repository.EmployeeRepository;
import com.sirius.springenablement.meetingScheduler.repository.RoomsRepository;
import com.sirius.springenablement.meetingScheduler.repository.TeamsRepository;
import com.sirius.springenablement.meetingScheduler.repository.TimeSlotRepository;
import com.sirius.springenablement.meetingScheduler.services.EmployeeServiceImpl;
import com.sirius.springenablement.meetingScheduler.services.MeetingServiceImpl;
import com.sirius.springenablement.meetingScheduler.services.TeamServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for meeting Services
 */
@ExtendWith(MockitoExtension.class)
class MeetingServiceImplTest {

    @Mock
    private TimeSlotRepository timeSlotRepository;
    @Mock
    private TeamsRepository teamsRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private RoomsRepository roomsRepository;
    @Mock
    private EmployeeServiceImpl employeeService;

    @Mock
    private TeamServiceImpl teamService;

    @InjectMocks
    private MeetingServiceImpl meetingService;
    @InjectMocks
    private MeetingController meetingController;

    @Test
    void testDeleteMeeting() {
        int timeSlotId = 1;
        TimeSlot timeSlot = new TimeSlot();

        LocalTime currentTime = LocalTime.now();
        LocalTime newTime = currentTime.plusMinutes(10);

        timeSlot.setStart_time(newTime);

        when(timeSlotRepository.findById(timeSlotId)).thenReturn(Optional.of(timeSlot));

        MeetingBookingResponseDto result = meetingService.deleteMeeting(timeSlotId);

        assertEquals("Current time is not before 30 minutes before start time.", result.getMessage());
    }

    /**
     * To test whether delete method working or not
     */
    @Test
    void testDeleteAfterMeeting() {
        int timeSlotId = 1;
        TimeSlot timeSlot = new TimeSlot();

        LocalTime currentTime = LocalTime.now();
        LocalTime newTime = currentTime.plusMinutes(40);

        timeSlot.setStart_time(newTime);

        when(timeSlotRepository.findById(timeSlotId)).thenReturn(Optional.of(timeSlot));

        MeetingBookingResponseDto result = meetingService.deleteMeeting(timeSlotId);

        assertEquals("Meeting Canclled", result.getMessage());
    }

    /**
     * To test whether booking for team method is working or not
     */
    @Test
    void testBookMeetingForTeam() {
        int employeeId = 1;
        int teamId = 2;
        String bookingType = "TEAM";
        List<Integer> collaborators = null;

        Employee employee = new Employee();
        employee.setId(employeeId);

        Teams team = new Teams();
        team.setId(teamId);


        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);
        team.setEmployee(employeeList);

        when(employeeService.findById(employeeId)).thenReturn(Optional.of(employee));
        when(teamService.findById(teamId)).thenReturn(Optional.of(team));
        Rooms rooms = new Rooms();
        String roomName = "chennai";
        when(roomsRepository.findByRoomName(roomName)).thenReturn(rooms);
        TimeSlot bookingRequest = new TimeSlot();

        MeetingBookingResponseDto responseDto = meetingService.bookMeeting(
                bookingRequest, employeeId, bookingType, teamId, roomName, collaborators);
        assertEquals("Meeting booked for team.", responseDto.getMessage());
    }


    /**
     * to test whether the available rooms is working properly or not
     */

    @Test
    void testGetAvailableRoomsNow() {
        LocalDate date = LocalDate.now();
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(12, 0);
        int requiredCapacity = 10;

        Rooms availableRoom = new Rooms();
        availableRoom.setId(1);
        availableRoom.setRoomName("Chennai");
        availableRoom.setRoomCapacity(1);

        List<Rooms> allRooms = Collections.singletonList(availableRoom);
        when(roomsRepository.findAll()).thenReturn(allRooms);

        ResponseEntity<AvailableRoomsResponseDto> responseEntity =
                meetingService.getAvailableRoomsNow(date, startTime, endTime, requiredCapacity);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }



}