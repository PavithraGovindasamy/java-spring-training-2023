package com.sirius.springenablement.demo.controller;

import com.sirius.springenablement.demo.entity.Employee;
import com.sirius.springenablement.demo.entity.Teams;
import com.sirius.springenablement.demo.services.EmployeeService;
import com.sirius.springenablement.demo.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    public TeamService teamService;

    @Autowired
    public EmployeeService employeeService;

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

     @PostMapping("/{teamId}/addEmployee/{employeeId}")
        public ResponseEntity<String> addEmployeeToTeam(@PathVariable int teamId, @PathVariable int employeeId) {
        System.out.println("ey");
//
//         Optional<Teams> teams=teamService.findById(teamId);
//         if(teams.isPresent()){
//             Teams dbteams = teams.get();
//           Employee employee=employeeService.findById(employeeId).orElse(null);
//
//             dbteams.setEmployee(Collections.singletonList(employee));
//
//         }
            teamService.addEmployeeToTeam(employeeId, teamId);


            return ResponseEntity.ok("Employee added to team");
        }

    // Adding a new team
    @PostMapping("/addTeams")
    public Teams addTeams(@RequestBody Teams teams){
        Teams dbTeams=teamService.save(teams);
        return dbTeams;
    }

    // updating a new Team
    @PutMapping("/updateTeams")
    public  Teams updateTeams(@RequestBody Teams teams){
        Teams updateTeams=teamService.save(teams);
        return  updateTeams;
    }

    // Deleting a team by id


    @DeleteMapping("/delete/{teamId}")
    public String deleteTeams(@PathVariable int teamId){
        Optional<Teams> deleteTeams=teamService.findById(teamId);
        if(deleteTeams.isPresent()){
            teamService.deleteById(teamId);
        }
        return  "deleted Team with ID" + teamId;
    }

}
