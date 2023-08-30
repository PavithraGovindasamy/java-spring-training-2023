package com.sirius.springenablement.meetingScheduler.services;

import com.sirius.springenablement.meetingScheduler.dto.AvailableRoomsResponseDto;
import com.sirius.springenablement.meetingScheduler.dto.MeetingBookingResponseDto;
import com.sirius.springenablement.meetingScheduler.dto.RoomsResponseDto;
import com.sirius.springenablement.meetingScheduler.entity.Employee;
import com.sirius.springenablement.meetingScheduler.entity.Rooms;
import com.sirius.springenablement.meetingScheduler.entity.Teams;
import com.sirius.springenablement.meetingScheduler.entity.TimeSlot;
import com.sirius.springenablement.meetingScheduler.repository.RoomsRepository;
import com.sirius.springenablement.meetingScheduler.repository.TimeSlotRepository;
import com.sirius.springenablement.meetingScheduler.req.UpdateMeetingRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service which implements the functionalities for booking a meeting
 */
@Service
public class MeetingServiceImpl implements MeetingService {

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private RoomsRepository roomsRepository;

    @Autowired
    private TeamService teamService;


    @Autowired
    private EmployeeService employeeService;

    /**
     * Method which sorts the available rooms
     *
     * @param date
     * @param startTime
     * @param endTime
     * @param requiredCapacity
     * @return
     */
    public ResponseEntity<AvailableRoomsResponseDto> getAvailableRoomsNow(LocalDate date, LocalTime startTime, LocalTime endTime, int requiredCapacity) {
        try {
            List<Rooms> allRooms = roomsRepository.findAll();

            Optional<Rooms> availableRoom = allRooms.stream()
                    .filter(room -> room.getRoomCapacity() >= requiredCapacity && roomAvailable(room, date, startTime, endTime))
                    .findFirst();

            AvailableRoomsResponseDto responseDto = new AvailableRoomsResponseDto();

            if (availableRoom.isPresent()) {
                Rooms availableRoomObject = availableRoom.get();

                responseDto.setSuccess(true);
                responseDto.setMessage("Room is available.");

                RoomsResponseDto roomResponseDto = new RoomsResponseDto();
                BeanUtils.copyProperties(availableRoomObject, roomResponseDto);

                responseDto.setAvailableRoom(roomResponseDto);
            } else {
                responseDto.setSuccess(false);
                responseDto.setMessage("No room available.");
            }

            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            AvailableRoomsResponseDto responseDto = new AvailableRoomsResponseDto();
            responseDto.setSuccess(false);
            responseDto.setMessage("Failed to fetch available room.");
            return ResponseEntity.badRequest().body(responseDto);
        }
    }





    /**
     * Method which books team for an employee
     *
     * @param employee
     * @param teams
     * @param meeting
     * @param roomName
     * @return
     */
    public MeetingBookingResponseDto bookMeetingForTeam(Employee employee, Teams teams, TimeSlot meeting, String roomName) {
        MeetingBookingResponseDto responseDto = new MeetingBookingResponseDto();
        try {
            Rooms rooms = roomsRepository.findByRoomName(roomName);

            if (rooms == null) {
                responseDto.setSuccess(false);
                responseDto.setMessage("Room not found.");
                return responseDto;
            }
            if (!teams.getEmployee().contains(employee)) {
                responseDto.setSuccess(false);
                responseDto.setMessage("Employee is not a member of the specified team.");
                return responseDto;
            }
            if (employee != null) {
                meeting.setEmployee(employee);
                meeting.setTeams(Collections.singletonList(teams));
            }

            List<Rooms> roomsList = new ArrayList<>();
            if (rooms != null) {
                roomsList.add(rooms);
                meeting.setRooms(roomsList);
            }

            if (!roomAvailable(rooms, meeting.getDate(), meeting.getStart_time(), meeting.getEnd_time())) {

                responseDto.setSuccess(false);
                responseDto.setMessage("Sorry, the room is not available");
                return responseDto;
            }

            List<Employee> notAvailableMembers = notAvailableMembers(teams, meeting.getDate(), meeting.getStart_time(), meeting.getEnd_time());

            TimeSlot savedMeeting = timeSlotRepository.save(meeting);

            responseDto.setSuccess(true);

            if (notAvailableMembers != null && !notAvailableMembers.isEmpty()) {
                responseDto.setMessage("Meeting booked for team. Unavailable members: " + notAvailableMembers);
            } else {
                responseDto.setSuccess(true);
                responseDto.setMessage("Meeting booked for team.");
            }
        } catch (Exception exception) {
            responseDto.setSuccess(false);
            responseDto.setMessage("An error occurred.");
        }
        return responseDto;
    }


    /**
     * Method which books meeting for a collaboration in a company
     *
     * @param employee
     * @param meeting
     * @param roomName
     * @param collaboratorsTeam
     * @return
     */

    public MeetingBookingResponseDto bookMeetingForCollaboration(Employee employee, TimeSlot meeting, String roomName, Teams collaboratorsTeam) {
        Rooms rooms = roomsRepository.findByRoomName(roomName);
        MeetingBookingResponseDto responseDto = new MeetingBookingResponseDto();
        try {
            if (rooms == null) {
                responseDto.setSuccess(false);
                responseDto.setMessage("Room not found.");
                return responseDto;
            }

            if (employee != null) {
                meeting.setEmployee(employee);
            }

            List<Rooms> roomsList = new ArrayList<>();
            if (rooms != null) {
                roomsList.add(rooms);
                meeting.setRooms(roomsList);
            }


            if (!roomAvailable(rooms, meeting.getDate(), meeting.getStart_time(), meeting.getEnd_time())) {

                responseDto.setSuccess(false);
                responseDto.setMessage("Sorry, the room is not available");
                return responseDto;
            }
            List<Employee> notAvailableMembers = notAvailableMembers(collaboratorsTeam, meeting.getDate(), meeting.getStart_time(), meeting.getEnd_time());



            // Save the meeting
            timeSlotRepository.save(meeting);


            if (notAvailableMembers != null && !notAvailableMembers.isEmpty()) {
                responseDto.setMessage("Meeting booked for Collaboration. Unavailable members: " + notAvailableMembers);
            } else {
                responseDto.setSuccess(true);
                responseDto.setMessage("Meeting booked for Collaboration.");
            }
        }
        catch (Exception exception) {
            responseDto.setSuccess(false);
            responseDto.setMessage("An error occurred.");
        }

        return responseDto;
    }



    /**
     * Method that checks whether a particular member is available or not
     *
     * @param teams
     * @param date
     * @param startTime
     * @param endTime
     * @return
     */
    public List<Employee> notAvailableMembers(Teams teams, LocalDate date, LocalTime startTime, LocalTime endTime) {
        return teams.getEmployee().stream()
                .filter(teamMember -> {
                    List<TimeSlot> bookedSlots = timeSlotRepository.findByTeamsAndDateAndStartTimeLessThanAndEndTimeGreaterThan(
                            teams, date, endTime, startTime
                    );
                    return !bookedSlots.isEmpty();
                })
                .collect(Collectors.toList());
    }


    /**
     * Method which checks whether that particular room is available or not
     *
     * @param rooms
     * @param date
     * @param startTime
     * @param endTime
     * @return
     */
    public boolean roomAvailable(Rooms rooms, LocalDate date, LocalTime startTime, LocalTime endTime) {
        List<TimeSlot> booked = timeSlotRepository.findByRoomsAndDateAndStartTimeLessThanAndEndTimeGreaterThan(
                rooms, date, endTime, startTime
        );


        if (!booked.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * @param timeSlotId-- Deleting the meeting details
     * @return
     */

    public MeetingBookingResponseDto deleteMeeting(int timeSlotId) {
        MeetingBookingResponseDto responseDto = new MeetingBookingResponseDto();
        try {
            TimeSlot timeSlot = timeSlotRepository.findById(timeSlotId).orElse(null);
            LocalTime start_time = timeSlot.getStart_time();
            LocalTime currentTime = LocalTime.now();
            LocalTime newTime = start_time.minus(30, ChronoUnit.MINUTES);
            if (currentTime.isBefore(newTime)) {
                timeSlotRepository.deleteById(timeSlotId);
                responseDto.setSuccess(true);
                responseDto.setMessage("Meeting Canclled");
                return responseDto;
            } else {
                responseDto.setSuccess(true);
                responseDto.setMessage("Current time is not before 30 minutes before start time.");
                return responseDto;
            }
        }catch (Exception exception) {
            responseDto.setSuccess(false);
            responseDto.setMessage("An error occurred.");
            return responseDto;
        }

    }

    /**
     *
     * @param request
     * @return
     */

    @Transactional
    public MeetingBookingResponseDto updateMeeting(UpdateMeetingRequest request) {
        MeetingBookingResponseDto responseDto = new MeetingBookingResponseDto();

        try {
            TimeSlot savedTimeSlot = timeSlotRepository.findById(request.getTimeSlotId()).orElse(null);

            if (savedTimeSlot != null) {


                Rooms newRoom = roomsRepository.findByRoomName(request.getRoomName());

                if (newRoom == null) {
                    responseDto.setSuccess(false);
                    responseDto.setMessage("New room not found.");
                    return responseDto;
                }

                boolean roomIsAvailable = roomAvailable(newRoom, request.getBookingRequest().getDate(),
                        request.getBookingRequest().getStart_time(), request.getBookingRequest().getEnd_time());

                if (!roomIsAvailable) {
                    responseDto.setSuccess(false);
                    responseDto.setMessage("New room is not available for the selected time.");
                    return responseDto;

                }
                else {
                    savedTimeSlot.setDate(request.getBookingRequest().getDate());
                savedTimeSlot.setStart_time(request.getBookingRequest().getStart_time());
                savedTimeSlot.setEnd_time(request.getBookingRequest().getEnd_time());
                    // Update time slot's room
                    List<Rooms> updatedRoomsList = new ArrayList<>();
                    updatedRoomsList.add(newRoom);
                    savedTimeSlot.setRooms(updatedRoomsList);

                    // Update added employees' time slots
                    if (request.getAddedEmployees() != null) {
                        for (Integer employeeId : request.getAddedEmployees()) {
                            Employee employee = employeeService.findById(employeeId).orElse(null);
                            if (employee != null) {
                                employee.getTimeSlots().add(savedTimeSlot);
                            } else {
                                // Handle the case where employee is not found
                            }
                        }
                    }

                    if (request.getRemovedEmployees() != null) {
                        for (Integer employeeId : request.getRemovedEmployees()) {
                            Employee employee = employeeService.findById(employeeId).orElse(null);
                            if (employee != null && employee.getTimeSlots().contains(savedTimeSlot)) {
                                employee.getTimeSlots().remove(savedTimeSlot);
                            } else {
                            }
                        }
                    }

                    timeSlotRepository.save(savedTimeSlot);

                    responseDto.setSuccess(true);
                    responseDto.setMessage("Meeting details and room updated.");
                }
            } else {
                responseDto.setSuccess(false);
                responseDto.setMessage("Meeting not found.");
            }
        } catch (Exception exception) {
            responseDto.setSuccess(false);
            responseDto.setMessage("An error occurred while updating the meeting.");
            exception.printStackTrace();
        }

        return responseDto;
    }




    /**
     * Method which implements the logic for booking  a meeting
     *
     * @param bookingRequest
     * @param employeeId
     * @param bookingType
     * @param teamId
     * @param roomName
     * @param collaborators
     * @return
     */
    public MeetingBookingResponseDto bookMeeting(TimeSlot bookingRequest, int employeeId, String bookingType,
                                                 Integer teamId, String roomName,
                                                 List<Integer> collaborators) {
        MeetingBookingResponseDto responseDto = new MeetingBookingResponseDto();
        try {
            Employee employee = employeeService.findById(employeeId).orElse(null);

            if (employee == null) {
                responseDto.setSuccess(false);
                responseDto.setMessage("Employee not found.");
            }

            if ("TEAM".equals(bookingType)) {
                Teams teams = teamService.findById(teamId).orElse(null);
                if (teams == null) {
                    responseDto.setSuccess(false);
                    responseDto.setMessage("Team not found.");
                    return responseDto;
                }
                responseDto = bookMeetingForTeam(employee, teams, bookingRequest, roomName);
            } else if ("COLLABORATION".equals(bookingType)) {
                if (collaborators != null && !collaborators.isEmpty()) {
                    // collaborators team
                    Teams collaboratorsTeam = new Teams();
                    collaboratorsTeam.setTeamName("Collaborators Team");
                    collaboratorsTeam.setTeamCapacity(collaborators.size());
                    collaboratorsTeam.setEmployee(new ArrayList<>());
                    List<Employee> collaboratorsList = collaborators.stream()
                            .map(collaboratorId -> employeeService.findById(collaboratorId).orElse(null))
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());

                    collaboratorsTeam.getEmployee().addAll(collaboratorsList);

                    teamService.save(collaboratorsTeam);

                    responseDto = bookMeetingForCollaboration(employee, bookingRequest, roomName, collaboratorsTeam);
                }
            }
        } catch (Exception exception) {
            responseDto.setSuccess(false);
            responseDto.setMessage("An error occurred.");
        }
        return responseDto;
    }

    /**
     * Method to get ids
     */
    public List<Integer> getRoomIds(List<Rooms> allRooms) {
        List<Integer> roomIdsList = allRooms.stream()
                .map(Rooms::getId)
                .collect(Collectors.toList());

        return roomIdsList;
    }


}
