package com.sirius.springenablement.demo.dao;

import com.sirius.springenablement.demo.entity.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class AppDaoImp  implements AppDao{

    private EntityManager entityManager;
    public  AppDaoImp(EntityManager entityManager){
        this.entityManager=entityManager;
    }


    @Override
    @Transactional
    public void save(Course instructor) {
        entityManager.persist(instructor);
    }

    @Override
    public Course findCourseAndStudentByCourseId(int id) {

        TypedQuery<Course> query=entityManager.createQuery("select c from Course c"+"JOIN FETCH c.student"+"where c.id=:data",Course.class);
        query.setParameter("data",id);
        Course course=query.getSingleResult();
        return  course;
    }

//    @Override
//    public Instructor findById(int id) {
//        return entityManager.find(Instructor.class,id); // retrieve both instructor and details be defined as eager-->retrives everyting
//    }
//
//    @Override
//    @Transactional
//    public void delete(int id) {
//        Instructor del=entityManager.find(Instructor.class,id);
//        entityManager.remove(del);
//    }
//
//    @Override
//    @Transactional
//    public InstructorDetail findInsDetailById(int id) {
//        return entityManager.find(InstructorDetail.class,id);
//    }
}
