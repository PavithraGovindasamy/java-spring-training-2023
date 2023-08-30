package com.sirius.springenablement.demo.rest;

import com.sirius.springenablement.demo.dao.EmployeeDAO;
import com.sirius.springenablement.demo.entity.Employee;
import com.sirius.springenablement.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeRestController {
    private EmployeeService employeeService;

    @Autowired
    public EmployeeRestController(EmployeeService employeeService){
        this.employeeService=employeeService;
    }

    @GetMapping("/employees")
    public List<Employee> findAll(){
        return  employeeService.findAll();
    }

    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable int employeeId){
       Employee employee=employeeService.findById(employeeId);
       if(employee==null) {
           throw new RuntimeException("Employee not found" + employee);

       }
       return  employee;
    }


// creating new employee
    @PostMapping("/employees")
    public  Employee addEmployee(@RequestBody Employee employee){
        employee.setId(0);
        Employee employee1=employeeService.save(employee);
        return  employee1;
    }

    // update existing employee

    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee employee){
        Employee employee2=employeeService.save(employee);
        return  employee2;
    }

    // delete employee
    @DeleteMapping("/employees/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId){
        Employee employee=employeeService.findById(employeeId);
        if(employee==null) {
            throw new RuntimeException("Employee not found" + employee);

        }
        employeeService.deleteById(employeeId);
        return "delete employee id" + employeeId;
    }

}
