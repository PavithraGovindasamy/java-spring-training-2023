package com.cdw.springenablement.helperapp.repository;

import com.cdw.springenablement.helperapp.entity.Helper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository which handles helper related operation
 */
public interface HelperRepository extends JpaRepository<Helper, Long>{
    List<Helper> findByIdNotIn(List<Long> bookedHelperIds);

    Helper findByUserId(Long helperId1);
}
