package com.sirius.springenablement.meetingScheduler.repository;

import com.sirius.springenablement.meetingScheduler.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface that defines that EmployeeRepository methods
 */
//spring will create endpoints by making the first letter as small and adding a yes
@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
}
