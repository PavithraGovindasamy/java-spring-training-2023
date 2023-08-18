package com.sirius.springenablement.demo.controller;
import com.sirius.springenablement.demo.entity.Employee;
import com.sirius.springenablement.demo.entity.Rooms;
import com.sirius.springenablement.demo.entity.Teams;
import com.sirius.springenablement.demo.entity.TimeSlot;
import com.sirius.springenablement.demo.repository.RoomsRepository;
import com.sirius.springenablement.demo.repository.TimeSlotRepository;
import com.sirius.springenablement.demo.services.EmployeeService;
import com.sirius.springenablement.demo.services.TeamService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/meeting")
public class MeetingController {

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private RoomsRepository roomsRepository;

    @GetMapping("/availableRooms")
    public String getAvailableRooms(@RequestBody TimeSlot bookingRequest, @RequestParam int requiredCapacity) {
            List<Rooms> availableRooms = getAvailableRoomsNow(bookingRequest.getDate(), bookingRequest.getStart_time(), bookingRequest.getEnd_time(), requiredCapacity);
            return"okay " + availableRooms;
    }


    private List<Rooms> getAvailableRoomsNow(String date, LocalTime startTime, LocalTime endTime, int requiredCapacity) {
        List<Rooms> availableRoomsNow = new ArrayList<>();
        List<Rooms> allRooms = roomsRepository.findAll();
        List<Rooms> nearestCapacityRooms = new ArrayList<>();

        for (Rooms room : allRooms) {
            if (!roomAvailable(room, date, startTime, endTime)) {
             System.out.println("Not available");
            }
            availableRoomsNow.add(room);
            if (room.getRoomCapacity() >= requiredCapacity) {
                System.out.println(room);
                nearestCapacityRooms.add(room);
            }
        }
        if (availableRoomsNow.isEmpty() && !nearestCapacityRooms.isEmpty()) {
            availableRoomsNow = nearestCapacityRooms;
        }

        availableRoomsNow.sort(Comparator.comparingInt(Rooms::getRoomCapacity));

        return availableRoomsNow;



    }




    /**
     * Method which books meeting for a employee
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
    public String bookMeeting(@RequestBody TimeSlot bookingRequest, @PathVariable int employeeId, @PathVariable String bookingType,
                              @RequestParam(required = false) Integer teamId,@RequestParam String roomName,
                              @RequestParam(required = false) List<Integer> collaborators) {
        Employee employee = employeeService.findById(employeeId).orElse(null);

        if (employee == null) {
            return "Employee not found.";
        }
        if ("TEAM".equals(bookingType) ) {
            Teams teams=teamService.findById(teamId).orElse(null);
            if (teams == null) {
                return "Team not found.";
            }
            return bookMeetingForTeam(employee, teams, bookingRequest, roomName);
        }

        else if ("COLLABORATION".equals(bookingType)) {
            if (collaborators != null && !collaborators.isEmpty()) {
              // collaborators team
                Teams collaboratorsTeam = new Teams();
                collaboratorsTeam.setTeamName("Collaborators Team");
                collaboratorsTeam.setTeamCapacity(collaborators.size());
                collaboratorsTeam.setEmployee(new ArrayList<>());

                for (Integer collaboratorId : collaborators) {
                    Employee collaborator = employeeService.findById(collaboratorId).orElse(null);
                    System.out.println(collaborator);
                    if (collaborator != null) {
                        collaboratorsTeam.getEmployee().add(collaborator);
                    }
                }
                teamService.save(collaboratorsTeam);

                return bookMeetingForCollaboration(employee, bookingRequest, roomName, collaboratorsTeam);
            }

        }
        else {
            System.out.println(bookingType);
            return "Invalid booking type.";
        }
        return bookingType;
    }

    /**
     * Method which books team for an employee
     * @param employee
     * @param teams
     * @param meeting
     * @param roomName
     * @return
     */
    private String bookMeetingForTeam(Employee employee, Teams teams, TimeSlot meeting, String roomName) {

        Rooms rooms = roomsRepository.findByRoomName(roomName);
        if (rooms == null) {
            return "Room not found.";
        }
        if(employee!=null) {
            meeting.setEmployee(employee);
            meeting.setTeams(Collections.singletonList(teams));
        }
        List<Rooms> roomsList = new ArrayList<>();
        if (rooms != null) {
            roomsList.add(rooms);
            meeting.setRooms(roomsList);
            System.out.println(roomsList);
        }
        // Check if the room is available
        if (!roomAvailable(rooms, meeting.getDate(), meeting.getStart_time(), meeting.getEnd_time())) {
            return "Sorry, the room is not available";
        }
        // Check if team members are available
        List<Employee> notAvailableMembers = notAvailableMembers(teams, meeting.getDate(), meeting.getStart_time(), meeting.getEnd_time());
        // Save the meeting
        TimeSlot savedMeeting = timeSlotRepository.save(meeting);
        savedMeeting.setBooked(true);
        return "Meeting booked for team." + "Unavailable members" + notAvailableMembers;
    }

    /**
     *
     * Method which books meeting for a collaboration in a company
     * @param employee
     * @param meeting
     * @param roomName
     * @param collaborators
     * @return
     */
    private String bookMeetingForCollaboration(Employee employee, TimeSlot meeting, String roomName, Teams collaborators) {
        Rooms rooms = roomsRepository.findByRoomName(roomName);
        if (rooms == null) {
            return "Room not found.";
        }
        if(employee!=null) {
            meeting.setEmployee(employee);
        }
        List<Rooms> roomsList = new ArrayList<>();
        if (rooms != null) {
            roomsList.add(rooms);
            meeting.setRooms(roomsList);
            System.out.println(roomsList);
        }

        if (!roomAvailable(rooms, meeting.getDate(), meeting.getStart_time(), meeting.getEnd_time())) {
            return "Sorry, the room is not available";
        }
        // Save the meeting
        TimeSlot savedMeeting = timeSlotRepository.save(meeting);
        savedMeeting.setBooked(true);

        return "Meeting booked for collaboration.";
    }

    /**
     * Method that checks whether a particular member is available or not
     * @param teams
     * @param date
     * @param startTime
     * @param endTime
     * @return
     */
    private List<Employee> notAvailableMembers(Teams teams, String date, LocalTime startTime, LocalTime endTime) {

        List<Employee> unavailableMembers = new ArrayList<>();
        for (Employee teamMember : teams.getEmployee()) {
            List<TimeSlot> bookedSlots = timeSlotRepository.findByTeamsAndDateAndStartTimeLessThanAndEndTimeGreaterThan(
                    teams, date, endTime, startTime
            );
            if (!bookedSlots.isEmpty()) {
                unavailableMembers.add(teamMember);
                System.out.println("Booked here " + teamMember.getTimeSlots());
            }
        }
        return unavailableMembers;
    }

    /**
     * Method which checks whether that particular room is available or not
     * @param rooms
     * @param date
     * @param startTime
     * @param endTime
     * @return
     */

    private boolean roomAvailable(Rooms rooms, String date, LocalTime startTime, LocalTime endTime) {
        List<TimeSlot> booked = timeSlotRepository.findByRoomsAndDateAndStartTimeLessThanAndEndTimeGreaterThan(
                rooms, date, endTime, startTime
        );
        if (!booked.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param timeSlotId-- Deleting the meeting details
     * @return
     */

    @DeleteMapping("/delete/{timeSlotId}")
    public  String deleteMeeting(@PathVariable int  timeSlotId){

        TimeSlot timeSlot=timeSlotRepository.findById(timeSlotId).orElse(null);
        LocalTime start_time=timeSlot.getStart_time();
        LocalTime currentTime=LocalTime.now();
        LocalTime newTime = start_time.minus(30, ChronoUnit.MINUTES);
        if (currentTime.isBefore(newTime)) {
            timeSlotRepository.deleteById(timeSlotId);
            return "Meeting cancelled";
        } else {

           return "Current time is not before 30 minutes before start time.";
        }
    }

    /**
     *
     * @param timeSlotId
     * @param bookingRequest -> updating the details of meeting
     * @return
     */

    @PutMapping("/update/{timeSlotId}")
    public  String updateMeeting(@PathVariable int timeSlotId,@RequestBody TimeSlot bookingRequest){

        TimeSlot timeSlot=timeSlotRepository.findById(timeSlotId).orElse(null);
        timeSlot.setId(timeSlotId);
        timeSlot.setEnd_time(bookingRequest.getEnd_time());
        timeSlot.setStart_time(bookingRequest.getStart_time());
        timeSlot.setDate(bookingRequest.getDate());
        timeSlot.setDescription(bookingRequest.getDescription());
        timeSlot.setEmployee(timeSlot.getEmployee());
        timeSlotRepository.save(timeSlot);
        return "Updated meeting details";
    }



}