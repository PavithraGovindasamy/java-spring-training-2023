package com.sirius.springenablement.demo.dao;

import com.sirius.springenablement.demo.entity.Course;

public interface AppDao {
    void save(Course instructor);
    Course findCourseAndStudentByCourseId(int id);

}
