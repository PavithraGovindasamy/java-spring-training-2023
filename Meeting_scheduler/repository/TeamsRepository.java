package com.sirius.springenablement.demo.repository;

import com.sirius.springenablement.demo.entity.Teams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamsRepository extends JpaRepository<Teams,Integer> {
}