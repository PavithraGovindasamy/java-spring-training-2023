package com.sirius.springenablement.demo.dao;

import com.sirius.springenablement.demo.entity.Student;

import java.util.List;

public interface StudentDAO {
    public  void save(Student student);

    Student findStudent(int i);

    List<Student> findAll();

    // updating the database of user
    void update(Student student);

    void delete(int i);
    // delete all
    void deleteAll();


}
