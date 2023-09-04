package com.sirius.springenablement.meetingScheduler.services;

import com.sirius.springenablement.meetingScheduler.repository.EmployeeRepository;
import com.sirius.springenablement.meetingScheduler.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sirius.springenablement.meetingScheduler.services.interfaces.EmployeeService;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    public EmployeeRepository employeeRepository;

    /**
     * Method which saves the employee
     *
     * @param employee
     * @return the saved employee
     */
    @Override
    public Employee save(Employee employee) {
        try {
            employeeRepository.save(employee);
            return employee;
        } catch (Exception e) {
            throw new com.sirius.springenablement.meetingScheduler.exception.InvalidException("Failed to save employee", e);
        }
    }

    /**
     * Method which fetches a employee by id
     *
     * @param employeeId
     * @return the optional employee
     */
    @Override
    public Optional<Employee> findById(int employeeId) {
        try {
            return employeeRepository.findById(employeeId);
        } catch (Exception e) {
            throw new com.sirius.springenablement.meetingScheduler.exception.InvalidException("Failed to fetch employee by ID", e);
        }
    }

    /**
     * Method which deletes a employee
     *
     * @param employeeId
     */
    @Override
    public void deleteById(int employeeId) {
        try {
            employeeRepository.deleteById(employeeId);
        } catch (Exception e) {
            throw new com.sirius.springenablement.meetingScheduler.exception.InvalidException("Failed to delete employee by ID", e);
        }
    }

    /**
     * Method which finds all the employees
     *
     * @return the list of all employees
     */
    @Override
    public List<Employee> findAll() {
        try {
            return employeeRepository.findAll();
        } catch (Exception e) {
            throw new com.sirius.springenablement.meetingScheduler.exception.InvalidException("Failed to fetch all employees", e);
        }
    }
}
