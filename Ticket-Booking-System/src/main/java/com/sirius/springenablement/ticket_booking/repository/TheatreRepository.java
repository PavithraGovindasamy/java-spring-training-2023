package com.sirius.springenablement.ticket_booking.repository;

import com.sirius.springenablement.ticket_booking.entity.Roles;
import  org.springframework.stereotype.Repository;
import com.sirius.springenablement.ticket_booking.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
@Repository
public interface TheatreRepository extends JpaRepository<Theatre, Long> {


   Roles findByName(String roleName);
}