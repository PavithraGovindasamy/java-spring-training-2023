package com.cdw.springenablement.helperapp.repository;

import com.cdw.springenablement.helperapp.entity.BlackListToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Repository which handles blacklisted token related operation
 */
@Repository
public interface BlackListTokenRepository extends JpaRepository<BlackListToken,Long> {
    BlackListToken findByToken(String token);
}
