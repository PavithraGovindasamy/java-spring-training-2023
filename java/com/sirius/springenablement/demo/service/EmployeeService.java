package com.sirius.springenablement.demo.service;

import com.sirius.springenablement.demo.entity.Employee;

import java.util.List;

public interface EmployeeService {
List<Employee> findAll();
    Employee findById(int id);
    Employee save(Employee employee);
    void  deleteById(int id);


}

