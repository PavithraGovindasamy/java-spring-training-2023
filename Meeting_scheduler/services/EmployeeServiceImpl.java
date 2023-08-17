package com.sirius.springenablement.demo.services;

import com.sirius.springenablement.demo.repository.EmployeeRepository;
import com.sirius.springenablement.demo.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements  EmployeeService{

    @Autowired
    public EmployeeRepository employeeRepository;
    @Override
    public Employee save(Employee employee) {
        System.out.println("employee"+employee);
        employeeRepository.save(employee);
       return  employee;

    }

    @Override
    public Optional<Employee> findById(int employeeId) {
        return employeeRepository.findById(employeeId);
    }

    @Override
    public  String deleteById(int employeeId) {
        employeeRepository.deleteById(employeeId);
        return "deleted"+ employeeId;
    }

    @Override
    public List<Employee> findAll() {
       return employeeRepository.findAll();

    }

}
