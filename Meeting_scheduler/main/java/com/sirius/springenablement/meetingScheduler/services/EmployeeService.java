package com.sirius.springenablement.meetingScheduler.services;

import com.sirius.springenablement.meetingScheduler.entity.Employee;

import java.util.List;
import java.util.Optional;

/**
 * Interface which implements the functionalities of crud operations of employee
 */
public interface EmployeeService {
    Employee save(Employee employee);
    Optional<Employee> findById(int employeeId);
    void deleteById(int employeeId);
    List<Employee> findAll();
}
