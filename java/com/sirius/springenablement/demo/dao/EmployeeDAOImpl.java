package com.sirius.springenablement.demo.dao;

import com.sirius.springenablement.demo.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDAOImpl implements  EmployeeDAO {

    private EntityManager entityManager;

    @Autowired
    public EmployeeDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


//    @Override
//    @Transactional
//    public void save(Student student) {
//        entityManager.persist(student);
//    }
//
//    @Override
//    public Student findStudent(int i) {
//       return entityManager.find(Student.class,i);
//
//    }

    @Override
    public List<Employee> findAll() {
        TypedQuery<Employee> query=entityManager.createQuery("FROM Employee", Employee.class);
        return query.getResultList();
    }

    @Override
    public Employee findById(int id) {
         Employee employee=entityManager.find(Employee.class,id);
         return  employee;
    }

    @Override
    public Employee save(Employee employee) {
      Employee dbEmployee=entityManager.merge(employee);
      return  dbEmployee;
    }

    @Override
    public void deleteById(int theid) {
        Employee deleteEmployee=entityManager.find(Employee.class,theid);
        entityManager.remove(deleteEmployee);
    }

}
