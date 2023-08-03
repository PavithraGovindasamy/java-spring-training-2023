package com.sirius.springenablement.demo.dao;

import com.sirius.springenablement.demo.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentDAOImpl implements  StudentDAO {

    private EntityManager entityManager;

    @Autowired
    public StudentDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    @Transactional
    public void save(Student student) {
        entityManager.persist(student);
    }

    @Override
    public Student findStudent(int i) {
       return entityManager.find(Student.class,i);

    }

    @Override
    public List<Student> findAll() {
        TypedQuery<Student> query=entityManager.createQuery("FROM Student order by lastName", Student.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void update(Student student) {
        entityManager.merge(student); // update teh student

    }

    @Override
    @Transactional
    public void delete(int id) {
        Student student=entityManager.find(Student.class,id);
        entityManager.remove(student);


    }

    @Override
    @Transactional
    public void deleteAll() {
       entityManager.createQuery("DELETE FROM Student").executeUpdate();
    }
}
