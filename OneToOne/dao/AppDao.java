package com.sirius.springenablement.demo.dao;

import com.sirius.springenablement.demo.entity.Instructor;
import com.sirius.springenablement.demo.entity.InstructorDetail;

public interface AppDao {
    void save(Instructor instructor);
    // finding by id
    Instructor findById(int id);

    // deleting
    void delete(int id);
// making-bi-directional
    InstructorDetail findInsDetailById(int id);
}
