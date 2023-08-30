package com.sirius.springenablement.meetingScheduler.controller;

import com.sirius.springenablement.meetingScheduler.dto.MeetingBookingResponseDto;
import com.sirius.springenablement.meetingScheduler.entity.Employee;
import com.sirius.springenablement.meetingScheduler.exception.CustomException;
import com.sirius.springenablement.meetingScheduler.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

/**
 * Controller class for handling Employee-related operations.
 * @author pavithra
 */
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    public EmployeeService employeeService;


    /**
     * Add a new employee.
     *
     * @param employee The employee to be added
     * @return Response indicating success or failure
     */

    @PostMapping("/")
    public ResponseEntity<MeetingBookingResponseDto> addEmployee(@RequestBody Employee employee) {
        MeetingBookingResponseDto responseDto = new MeetingBookingResponseDto();
        try {
            employeeService.save(employee);
            responseDto.setSuccess(true);
            responseDto.setMessage("Employee added");

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (Exception e) {
            throw new CustomException("Failed to add employee");
        }
    }


    /**
     * Update employee details.
     *
     * @param employee The updated employee details
     * @return Response indicating success or failure
     */

    @PutMapping("/")
    public ResponseEntity<MeetingBookingResponseDto> updateEmployee(@RequestBody Employee employee) {
        MeetingBookingResponseDto responseDto = new MeetingBookingResponseDto();
        try {
            Optional<Employee> optionalEmployee = employeeService.findById(employee.getId());
            if (optionalEmployee.isPresent()) {
                employee.setId(employee.getId());
                employeeService.save(employee);
                responseDto.setSuccess(true);
                responseDto.setMessage("Employee updated");
                return ResponseEntity.ok(responseDto);
            } else {
                responseDto.setSuccess(false);
                responseDto.setMessage("Employee not found");

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
            }
        } catch (Exception e) {
            throw new CustomException("Failed to update employee");
        }
    }

    /**
     * Delete an employee.
     *
     * @param employeeId The ID of the employee to be deleted
     * @return Response indicating success or failure
     */

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<MeetingBookingResponseDto> deleteEmployee(@PathVariable int employeeId) {
        MeetingBookingResponseDto responseDto = new MeetingBookingResponseDto();
        try {
            Optional<Employee> deleteEmployee = employeeService.findById(employeeId);
            if (deleteEmployee.isPresent()) {
                employeeService.deleteById(employeeId);
                responseDto.setSuccess(true);
                responseDto.setMessage("Deleted employee successfully");
                return ResponseEntity.ok(responseDto);
            } else {
                responseDto.setSuccess(false);
                responseDto.setMessage("Employee not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
            }
        } catch (Exception e) {
            throw new CustomException("Failed to delete employee");
        }
    }

}
