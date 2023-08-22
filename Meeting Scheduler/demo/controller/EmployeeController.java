package com.sirius.springenablement.demo.controller;

import com.sirius.springenablement.demo.entity.Employee;
import com.sirius.springenablement.demo.entity.Teams;
import com.sirius.springenablement.demo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
/**
 ** Class which has basic functionalities basic crud operations for a employee
 */

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    public EmployeeService employeeService;

    /**
     * Method which fetches all employees
     * @return
     */
    @GetMapping("/allEmployees")
    public List<Employee> getAllEmployees(){
        return employeeService.findAll();
    }

    /**
     * Method which fetches a employee
     * @param employeeId
     * @return
     */

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


    /**
     * Method that implements to Add a employee
     * @param employee
     * @return
     */

    @PostMapping("/addEmployees")
    public String addEmployee(@RequestBody Employee employee){
        Employee dbEmployee=employeeService.save(employee);
        return "Employed added";
    }


    /**
     * Method which updates details of employee
     * @param employeeId
     * @param employee
     * @return
     */
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

    /**
     * Method which deletes a employee
     * @param employeeId
     * @return
     */
    @DeleteMapping("/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId){
        Optional<Employee> deleteEmployee=employeeService.findById(employeeId);
        if(deleteEmployee.isPresent()){
            employeeService.deleteById(employeeId);
        }
        return  "deleted Employee with ID" + employeeId;
    }


}
