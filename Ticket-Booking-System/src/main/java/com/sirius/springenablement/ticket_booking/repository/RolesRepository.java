
package com.sirius.springenablement.ticket_booking.repository;
import  org.springframework.stereotype.Repository;
import com.sirius.springenablement.ticket_booking.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {


   Roles findByName(String roleName);
}