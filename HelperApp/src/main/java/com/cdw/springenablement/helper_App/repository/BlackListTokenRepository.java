package com.cdw.springenablement.helper_App.repository;

import com.cdw.springenablement.helper_App.entity.BlackListToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlackListTokenRepository extends JpaRepository<BlackListToken,Long> {
    BlackListToken findByToken(String token);
}
