package com.cdw.springenablement.helperapp.repository;
import com.cdw.springenablement.helperapp.client.models.ApprovalDto;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import  org.springframework.stereotype.Repository;
import com.cdw.springenablement.helperapp.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

/**
 * Repository which handles users related operation
 */
@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);

    List<Users> findByApproved(String approved, Sort id);

    List<Users> findByIdInAndApproved(List<Long> ids, String statusRegistered);

}