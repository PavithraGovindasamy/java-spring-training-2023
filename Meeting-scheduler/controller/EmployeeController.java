package com.sirius.springenablement.demo.controller;

import com.sirius.springenablement.demo.entity.Employee;
import com.sirius.springenablement.demo.entity.Teams;
import com.sirius.springenablement.demo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    public EmployeeService employeeService;

    @GetMapping("/allEmployees")
    public List<Employee> getAllEmployees(){
        return employeeService.findAll();
    }

    // fetching a employee
    @GetMapping("/{employeeId}")
    public String getEmployeeByID(@PathVariable int employeeId) {

        Optional<Employee> optionalEmployee = employeeService.findById(employeeId);


        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            String response = "Employee: " + employee.getFirstName() + " " + employee.getLastName() + "\n" +"ID :" + employee.getId();
            for (Teams team : employee.getTeams()) {
                response += "Belongs to team: " + team.getTeamName() + "\n";
            }
            return response;
        } else {
            return "Employee not found.";
        }
    }


    // adding a new Employee
    @PostMapping("/addEmployees")
    public Employee addEmployee(@RequestBody Employee employee){

        Employee dbEmployee=employeeService.save(employee);

        Teams teams=new Teams("Digitial velocity",12);
//        dbEmployee.setTeams((List<Teams>) teams);
        return dbEmployee;
    }


    // updating a new Employee
    @PutMapping("/updateEmployee/{employeeId}")
    public String updateEmployee(@PathVariable int employeeId, @RequestBody Employee employee){
        Optional<Employee> optionalEmployee = employeeService.findById(employeeId);
        if(optionalEmployee.isPresent()) {
            employee.setId(employeeId);
            Employee updateEmployee = employeeService.save(employee);
            return "updated" + employeeId;
        }
        else
        return "employee not found";
    }

    // Deleting a team by id

    @DeleteMapping("/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId){
        Optional<Employee> deleteEmployee=employeeService.findById(employeeId);
        if(deleteEmployee.isPresent()){
            employeeService.deleteById(employeeId);
        }
        return  "deleted Employee with ID" + employeeId;
    }



}
