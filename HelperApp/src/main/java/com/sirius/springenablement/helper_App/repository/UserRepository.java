package com.sirius.springenablement.helper_App.repository;
import  org.springframework.stereotype.Repository;
import com.sirius.springenablement.helper_App.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);

}