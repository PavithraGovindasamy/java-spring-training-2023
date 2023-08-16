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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/meeting")
public class MeetingController {
    // to book a meeting
    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    public EmployeeService employeeService;

    @Autowired
    public TeamService teamService;

    @Autowired
    public RoomsRepository roomsRepository;

    @PostMapping("/bookForTeam/{employeeId}/{teamId}")
    @Transactional
    public String bookMeeting(@PathVariable int employeeId,@PathVariable int teamId, @RequestBody TimeSlot meeting) {
        System.out.println(employeeId);
        // getting the team and employee info
        Employee employee = employeeService.findById(employeeId).orElse(null);
        Teams teams=teamService.findById(teamId).orElse(null);

        if (employee != null) {
            meeting.setEmployee(employee);

            meeting.setTeams(Collections.singletonList(teams));


            List<Rooms> roomsList = new ArrayList<>();
            Rooms rooms = roomsRepository.findById(7).orElse(null);
            if (rooms != null) {
                roomsList.add(rooms);
                meeting.setRooms(roomsList);
                System.out.println(roomsList);
            }

            List<Employee> notAvailableMembers=notAvailableMembers(teams,meeting.getDate());
            if(!notAvailableMembers.isEmpty()){
                System.out.println("these members are not available" +notAvailableMembers);
            }
            TimeSlot savedMeeting;
            if (!roomAvailable(rooms, meeting.getDate(),meeting.getStart_time(),meeting.getEnd_time())) {
                return "sorry room not available";
            }
            else {
                savedMeeting = timeSlotRepository.save(meeting);
                savedMeeting.setBooked(true);
            }

            System.out.println("empl" + savedMeeting.getEmployee());
            return "Meeting created with ID: " + savedMeeting.getId() + " booked by " + employeeId +"for employees" + teams.getEmployee()+
                    "for team" + teamId +"these members are not available " +notAvailableMembers;


        } else {
            return "Employee not found.";
        }
    }

    private List<Employee> notAvailableMembers(Teams teams, String date) {


        List<Employee> unavailableMembers = new ArrayList<>();

        for (Employee teamMember : teams.getEmployee()) {
            List<TimeSlot> bookedSlots = timeSlotRepository.findByEmployeeAndDate(
                    teamMember, date
            );
            if (!bookedSlots.isEmpty()) {
                unavailableMembers.add(teamMember);
                System.out.println("Booked here " + teamMember.getTimeSlots());
            }
        }
        return  unavailableMembers;
    }



    private boolean roomAvailable(Rooms rooms, String date, LocalTime startTime, LocalTime endTime) {
        List<TimeSlot> booked = timeSlotRepository.findByRoomsAndDateAndStartTimeGreaterThanEqualAndEndTimeLessThanEqual(
                rooms, date, startTime, endTime
        );
        System.out.println("BOoKED"+booked);

        if (!booked.isEmpty()) {

            return false;
        }

        return true;
    }



    @DeleteMapping("/delete/{timeSlotId}")
    public  String deleteMeeting(@PathVariable int  timeSlotId){

        TimeSlot timeSlot=timeSlotRepository.findById(timeSlotId).orElse(null);
        LocalTime start_time=timeSlot.getStart_time();
        LocalTime currentTime=LocalTime.now();
         System.out.println(timeSlot);

            timeSlotRepository.deleteById(timeSlotId);
            return "Meeting cancelled ";


    }





}





