package com.sirius.springenablement.ticket_booking.repository;
import  org.springframework.stereotype.Repository;
import com.sirius.springenablement.ticket_booking.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);

}