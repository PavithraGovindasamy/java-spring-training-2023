
package com.cdw.springenablement.helper_App.repository;
import  org.springframework.stereotype.Repository;
import com.cdw.springenablement.helper_App.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository which handles roles related operation
 */
@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {


   Roles findByName(String roleName);
}