package com.sirius.springenablement.demo;

import com.sirius.springenablement.demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//spring will create endpoints by making the first letter as small and adding a yes
@RepositoryRestResource(path="members")
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
}
