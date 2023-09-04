package com.sirius.springenablement.meetingScheduler.repository;

import com.sirius.springenablement.meetingScheduler.entity.Teams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface that defines that teamsRepository methods
 */
@Repository
public interface TeamsRepository extends JpaRepository<Teams,Integer> {
}