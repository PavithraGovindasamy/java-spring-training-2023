package com.sirius.springenablement.demo.services;

import com.sirius.springenablement.demo.repository.EmployeeRepository;
import com.sirius.springenablement.demo.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service that implements the crud operations
 */
@Service
public class EmployeeServiceImpl implements  EmployeeService{

    @Autowired
    public EmployeeRepository employeeRepository;

    /**
     * Method which saves the employee
     * @param employee
     * @return
     */
    @Override
    public Employee save(Employee employee) {
        System.out.println("employee"+employee);
        employeeRepository.save(employee);
       return  employee;

    }

    /**
     * Method which fetches a employee by id
     * @param employeeId
     * @return
     */
    @Override
    public Optional<Employee> findById(int employeeId) {
        return employeeRepository.findById(employeeId);
    }

    /**
     * Method which deletes a employee
     * @param employeeId
     * @return
     */
    @Override
    public  String deleteById(int employeeId) {
        employeeRepository.deleteById(employeeId);
        return "deleted"+ employeeId;
    }

    /**
     *  Method which finds all the employee
     * @return
     */

    @Override
    public List<Employee> findAll() {
       return employeeRepository.findAll();

    }

}
