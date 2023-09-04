package com.sirius.springenablement.meetingScheduler.controller;

import com.sirius.springenablement.meetingScheduler.dto.MeetingBookingResponseDto;
import com.sirius.springenablement.meetingScheduler.entity.Teams;
import com.sirius.springenablement.meetingScheduler.req.AddEmployeeToTeamRequest;
import com.sirius.springenablement.meetingScheduler.services.interfaces.EmployeeService;
import com.sirius.springenablement.meetingScheduler.services.interfaces.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.Optional;

/**
 * Class which has basic functionalities basic CRUD operations for a team
 * @author pavithra
 */
@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    public TeamService teamService;

    @Autowired
    public EmployeeService employeeService;

    /**
     * Method which adds an employee to a team
     *
     * @return
     */
    @PostMapping("/addEmployeeToTeam")
    public ResponseEntity<MeetingBookingResponseDto> addEmployeeToTeam(@RequestBody AddEmployeeToTeamRequest request) {
        MeetingBookingResponseDto responseDto = new MeetingBookingResponseDto();
        try {
            teamService.addEmployeeToTeam(request.getEmployeeId(), request.getTeamId());
            responseDto.setSuccess(true);
            responseDto.setMessage("Employee added to team");
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (Exception e) {
            e.printStackTrace();
            throw new com.sirius.springenablement.meetingScheduler.exception.InvalidException("Failed to add employee to team");
        }
    }

    /**
     * Method which adds a new team
     *
     * @param teams the team to be added
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<MeetingBookingResponseDto> addTeams(@RequestBody Teams teams) {
        MeetingBookingResponseDto responseDto = new MeetingBookingResponseDto();
        try {
            Teams dbTeams = teamService.save(teams);
            responseDto.setSuccess(true);
            responseDto.setMessage("Team Added successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (Exception e) {
            e.printStackTrace();
            throw new com.sirius.springenablement.meetingScheduler.exception.InvalidException("Failed to add team");
        }
    }

    /**
     * Method which updates the team details
     *
     * @param teams the team details to be updated
     * @return
     */
    @PutMapping("/")
    public ResponseEntity<MeetingBookingResponseDto> updateTeams(@RequestBody Teams teams) {
        MeetingBookingResponseDto responseDto = new MeetingBookingResponseDto();
        try {
            Teams updatedTeams = teamService.save(teams);
            responseDto.setSuccess(true);
            responseDto.setMessage("Team updated successfully");
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            e.printStackTrace();
            throw new com.sirius.springenablement.meetingScheduler.exception.InvalidException("Failed to update team");
        }
    }

    /**
     * Method which deletes a team
     *
     *
     *
     *
     * @param teamId the team ID to be deleted
     * @return
     */
    @DeleteMapping("/{teamId}")
    public ResponseEntity<MeetingBookingResponseDto> deleteTeams(@PathVariable int teamId) {
        MeetingBookingResponseDto responseDto = new MeetingBookingResponseDto();
        try {
            Optional<Teams> deleteTeams = teamService.findById(teamId);
            if (deleteTeams.isPresent()) {
                teamService.deleteById(teamId);
                responseDto.setSuccess(true);
                responseDto.setMessage("Deleted team successfully");
                return ResponseEntity.ok(responseDto);
            } else {
                responseDto.setSuccess(false);
                responseDto.setMessage("Team not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new com.sirius.springenablement.meetingScheduler.exception.InvalidException("Failed to delete team");
        }
    }
}
