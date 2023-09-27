
package com.sirius.springenablement.helper_App.repository;
import  org.springframework.stereotype.Repository;
import com.sirius.springenablement.helper_App.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {


   Roles findByName(String roleName);
}