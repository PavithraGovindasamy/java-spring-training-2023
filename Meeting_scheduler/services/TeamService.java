package com.sirius.springenablement.demo.services;

import com.sirius.springenablement.demo.entity.Teams;

import java.util.Optional;

public interface TeamService {
   Teams save(Teams teams);

    Optional<Teams> findById(int teamId);

    void deleteById(int teamId);

    void addEmployeeToTeam(int employeeId, int  teamId);
}
