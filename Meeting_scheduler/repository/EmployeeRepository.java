package com.sirius.springenablement.demo.repository;

import com.sirius.springenablement.demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

//spring will create endpoints by making the first letter as small and adding a yes
@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
}
