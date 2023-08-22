package com.sirius.springenablement.demo.controller;

import com.sirius.springenablement.demo.entity.Employee;
import com.sirius.springenablement.demo.entity.Teams;
import com.sirius.springenablement.demo.services.EmployeeService;
import com.sirius.springenablement.demo.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Class which has basic functionalities basic crud operations for a team
 */

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    public TeamService teamService;

    @Autowired
    public EmployeeService employeeService;

    /**
     * Method which get the team details based on teamId
     * @param teamId
     * @return
     */

    // Getting the teams details by ID
    @GetMapping("/{teamId}")
    public String getEmployeeAndTeams(@PathVariable int teamId) {
        Optional<Teams> optionalTeams = teamService.findById(teamId);
        if (optionalTeams.isPresent()) {
            Teams teams = optionalTeams.get();
            String response = "Teams: " + teams.getTeamName()+ " " + teams.getTeamCapacity() + "\n";
            for (Employee employee: teams.getEmployee()) {
                response += "Employees: " + employee.getFirstName() + "\n";
            }
            return response;
        } else {
            return "Team not found.";
        }
    }

    /**
     * Method which add an  employee to a team
     * @param teamId
     * @param employeeId
     * @return
     */

     @PostMapping("/{teamId}/addEmployee/{employeeId}")
        public ResponseEntity<String> addEmployeeToTeam(@PathVariable int teamId, @PathVariable int employeeId) {
            teamService.addEmployeeToTeam(employeeId, teamId);
            return ResponseEntity.ok("Employee added to team");
        }

    /**
     * Method which adds new team
     * @param teams
     * @return
     */

    @PostMapping("/addTeams")
    public Teams addTeams(@RequestBody Teams teams){
        Teams dbTeams=teamService.save(teams);
        return dbTeams;
    }

    /**
     * Method which updates the team details
     * @param teams
     * @return
     */

    @PutMapping("/updateTeams")
    public  Teams updateTeams(@RequestBody Teams teams){
        Teams updateTeams=teamService.save(teams);
        return  updateTeams;
    }

    /**
     * Method which deletes a team
     * @param teamId
     * @return
     */


    @DeleteMapping("/delete/{teamId}")
    public String deleteTeams(@PathVariable int teamId){
        Optional<Teams> deleteTeams=teamService.findById(teamId);
        if(deleteTeams.isPresent()){
            teamService.deleteById(teamId);
        }
        return  "deleted Team with ID" + teamId;
    }

}
