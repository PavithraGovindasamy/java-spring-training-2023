package com.sirius.springenablement.demo.services;
import com.sirius.springenablement.demo.entity.Employee;
import com.sirius.springenablement.demo.entity.Rooms;
import com.sirius.springenablement.demo.entity.Teams;
import com.sirius.springenablement.demo.entity.TimeSlot;
import com.sirius.springenablement.demo.repository.RoomsRepository;
import com.sirius.springenablement.demo.repository.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
     * @param date
     * @param startTime
     * @param endTime
     * @param requiredCapacity
     * @return
     */
    public List<Rooms> getAvailableRoomsNow(String date, LocalTime startTime, LocalTime endTime, int requiredCapacity) {
        List<Rooms> availableRoomsNow = new ArrayList<>();
        List<Rooms> allRooms = roomsRepository.findAll();
        List<Rooms> nearestCapacityRooms = new ArrayList<>();

        for (Rooms room : allRooms) {
            if (roomAvailable(room, date, startTime, endTime)) {
                availableRoomsNow.add(room);
                if (room.getRoomCapacity() >= requiredCapacity) {
                    System.out.println(room);
                    nearestCapacityRooms.add(room);
                }
            }
        }

        nearestCapacityRooms.sort(Comparator.comparingInt(Rooms::getRoomCapacity));
        return nearestCapacityRooms;
    }
    /**
     * Method which books team for an employee
     * @param employee
     * @param teams
     * @param meeting
     * @param roomName
     * @return
     */
    public String bookMeetingForTeam(Employee employee, Teams teams, TimeSlot meeting, String roomName) {
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

    public String bookMeetingForCollaboration(Employee employee, TimeSlot meeting, String roomName, Teams collaborators) {
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
    public List<Employee> notAvailableMembers(Teams teams, String date, LocalTime startTime, LocalTime endTime) {
        List<Employee> unavailableMembers = new ArrayList<>();

        for (Employee teamMember : teams.getEmployee()) {
            List<TimeSlot> bookedSlots = timeSlotRepository.findByEmployeeAndDateAndStartTimeLessThanAndEndTimeGreaterThan(
                    teamMember, date, endTime, startTime
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
    public boolean roomAvailable(Rooms rooms, String date, LocalTime startTime, LocalTime endTime) {
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

    public ResponseEntity<String> deleteMeeting(int timeSlotId) {
        TimeSlot timeSlot=timeSlotRepository.findById(timeSlotId).orElse(null);
        LocalTime start_time=timeSlot.getStart_time();
        LocalTime currentTime=LocalTime.now();
        LocalTime newTime = start_time.minus(30, ChronoUnit.MINUTES);
        if (currentTime.isBefore(newTime)) {
            timeSlotRepository.deleteById(timeSlotId);

            return ResponseEntity.ok("Meeting cancelled");
        } else {

            return ResponseEntity.ok("Current time is not before 30 minutes before start time.");
        }

    }



    /**
     * @param timeSlotId
     * @param bookingRequest -> updating the details of meeting
     * @param newRoomName,addedEmployees,removedEmployees
     * @return
     */

    public String updateMeeting(int timeSlotId, TimeSlot bookingRequest, String newRoomName,
                                List<Integer> addedEmployees, List<Integer> removedEmployees) {
        TimeSlot timeSlot = timeSlotRepository.findById(timeSlotId).orElse(null);

        Rooms newRoom = roomsRepository.findByRoomName(newRoomName);
        System.out.println(newRoom);

        if (!roomAvailable(newRoom, bookingRequest.getDate(), bookingRequest.getStart_time(), bookingRequest.getEnd_time())) {
            System.out.println(bookingRequest.getDate());
            return "New room is not available for the selected time.";
        }

        // Update the meeting details
        timeSlot.setEnd_time(bookingRequest.getEnd_time());
        timeSlot.setStart_time(bookingRequest.getStart_time());
        timeSlot.setDate(bookingRequest.getDate());
        timeSlot.setDescription(bookingRequest.getDescription());

        // Update the room details
        List<Rooms> newRoomList = new ArrayList<>();
        newRoomList.add(newRoom);
        timeSlot.setRooms(newRoomList);
        System.out.println("empl"+ bookingRequest.getEmployee()+"timeslot" + timeSlot.getEmployee());
        // Get the existing employee
        Employee existingEmployee = timeSlot.getEmployee();

        if (addedEmployees != null) {
            for (Integer employeeId : addedEmployees) {
                Employee employee = employeeService.findById(employeeId).orElse(null);
                if (employee != null) {
                    employee.getTimeSlots().add(timeSlot);
                }
            }
        }
        if(removedEmployees!=null){
            for(Integer employeeId: removedEmployees){
                Employee employee=employeeService.findById(employeeId).orElse(null);
                System.out.println(timeSlot);
                    if(employee!=null){
                        System.out.println( "jk" + employee.getTimeSlots());
                        employee.getTimeSlots().remove(timeSlot);
                        System.out.println( "hjk" + employee.getTimeSlots());
                    }
            }
        }


        // Save the updated meeting details
        timeSlotRepository.save(timeSlot);
        return "Updated meeting details and room.";
    }


    /**
     * Method which implements the logic for booking  a meeting
     * @param bookingRequest
     * @param employeeId
     * @param bookingType
     * @param teamId
     * @param roomName
     * @param collaborators
     * @return
     */
    public String bookMeeting(TimeSlot bookingRequest,  int employeeId,  String bookingType,
                               Integer teamId,String roomName,
                               List<Integer> collaborators) {
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


}
