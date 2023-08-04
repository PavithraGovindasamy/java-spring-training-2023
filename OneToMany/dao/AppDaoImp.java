package com.sirius.springenablement.demo.dao;

import com.sirius.springenablement.demo.entity.Instructor;
import com.sirius.springenablement.demo.entity.InstructorDetail;
import jakarta.persistence.EntityManager;
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
    public void save(Instructor instructor) {
        entityManager.persist(instructor);
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
