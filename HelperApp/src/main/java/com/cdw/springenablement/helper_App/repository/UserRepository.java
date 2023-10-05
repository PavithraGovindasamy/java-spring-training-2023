package com.cdw.springenablement.helper_App.repository;
import  org.springframework.stereotype.Repository;
import com.cdw.springenablement.helper_App.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);


    List<Users> findByApproved(String approved);


}