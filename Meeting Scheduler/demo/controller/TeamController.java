package com.sirius.springenablement.demo.controller;

import com.sirius.springenablement.demo.entity.Employee;
import com.sirius.springenablement.demo.entity.Teams;
import com.sirius.springenablement.demo.services.EmployeeService;
import com.sirius.springenablement.demo.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Class which has basic functionalities basic CRUD operations for a team
 */
@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    public TeamService teamService;

    @Autowired
    public EmployeeService employeeService;

    /**
     * Method which gets the team details based on teamId
     *
     * @param teamId the team ID
     * @return
     */
    @GetMapping("/{teamId}")
    public ResponseEntity<String> getEmployeeAndTeams(@PathVariable int teamId) {
        Optional<Teams> optionalTeams = teamService.findById(teamId);
        if (optionalTeams.isPresent()) {
            Teams teams = optionalTeams.get();
            String response = "Teams: " + teams.getTeamName() + " " + teams.getTeamCapacity() + "\n";
            for (Employee employee : teams.getEmployee()) {
                response += "Employees: " + employee.getFirstName() + "\n";
            }
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Team not found.");
        }
    }

    /**
     * Method which adds an employee to a team
     *
     * @param teamId     the team ID
     * @param employeeId the employee ID
     * @return
     */
    @PostMapping("/{teamId}/addEmployee/{employeeId}")
    public ResponseEntity<String> addEmployeeToTeam(@PathVariable int teamId, @PathVariable int employeeId) {
        teamService.addEmployeeToTeam(employeeId, teamId);
        return ResponseEntity.ok("Employee added to team");
    }

    /**
     * Method which adds a new team
     *
     * @param teams the team to be added
     * @return
     */
    @PostMapping("/addTeams")
    public ResponseEntity<Teams> addTeams(@RequestBody Teams teams) {
        Teams dbTeams = teamService.save(teams);
        return ResponseEntity.ok(dbTeams);
    }

    /**
     * Method which updates the team details
     *
     * @param teams the team details to be updated
     * @return
     */
    @PutMapping("/updateTeams")
    public ResponseEntity<Teams> updateTeams(@RequestBody Teams teams) {
        Teams updatedTeams = teamService.save(teams);
        return ResponseEntity.ok(updatedTeams);
    }

    /**
     * Method which deletes a team
     *
     * @param teamId the team ID to be deleted
     * @return
     */
    @DeleteMapping("/delete/{teamId}")
    public ResponseEntity<String> deleteTeams(@PathVariable int teamId) {
        Optional<Teams> deleteTeams = teamService.findById(teamId);
        if (deleteTeams.isPresent()) {
            teamService.deleteById(teamId);
            return ResponseEntity.ok("Deleted Team with ID " + teamId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Team not found.");
        }
    }
}
