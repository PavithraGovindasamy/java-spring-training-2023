package com.sirius.springenablement.demo.services;

import com.sirius.springenablement.demo.entity.Employee;

import java.util.List;
import java.util.Optional;

/**
 * Interface which implements the functionalities of crud operations of employee
 */
public interface EmployeeService {
    Employee save(Employee employee);
    Optional<Employee> findById(int employeeId);
    String deleteById(int employeeId);
    List<Employee> findAll();
}
