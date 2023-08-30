package com.sirius.springenablement.meetingScheduler.services;

import com.sirius.springenablement.meetingScheduler.entity.Teams;

import java.util.Optional;

/**
 * Interface which implements the team service
 */
public interface TeamService {
   Teams save(Teams teams);

    Optional<Teams> findById(int teamId);

    void deleteById(int teamId);

    void addEmployeeToTeam(int employeeId, int  teamId);
}
