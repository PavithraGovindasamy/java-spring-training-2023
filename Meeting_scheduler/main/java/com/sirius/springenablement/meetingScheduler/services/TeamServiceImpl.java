package com.sirius.springenablement.meetingScheduler.services;

import com.sirius.springenablement.meetingScheduler.entity.Employee;
import com.sirius.springenablement.meetingScheduler.exception.CustomException;
import com.sirius.springenablement.meetingScheduler.repository.EmployeeRepository;
import com.sirius.springenablement.meetingScheduler.repository.TeamsRepository;
import com.sirius.springenablement.meetingScheduler.entity.Teams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * Service that implements basic fucntionalities of team
 * @author pavithra
 */
@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    public TeamsRepository teamsRepository;

    @Autowired
    public EmployeeRepository employeeRepository;

    /**
     * Method which implements saving a team
     * @param teams
     * @return
     */
    @Override
    public Teams save(Teams teams) {
        try {
            return teamsRepository.save(teams);
        } catch (Exception e) {
            throw new CustomException("Failed to save team", e);
        }
    }

    /**
     * Method which fetches a particular team
     * @param teamId
     * @return
     */
    @Override
    public Optional<Teams> findById(int teamId) {
        try {
            return teamsRepository.findById(teamId);
        } catch (Exception e) {
            throw new CustomException("Failed to fetch team by ID", e);
        }
    }

    /**
     * Method which deletes a team
     * @param teamId
     */
    @Override
    public void deleteById(int teamId) {
        try {
            teamsRepository.deleteById(teamId);
        } catch (Exception e) {
            throw new CustomException("Failed to delete team by ID", e);
        }
    }

    /**
     * Method which adds Employee to Team
     * @param employeeId
     * @param teamId
     */
    @Override
    public void addEmployeeToTeam(int employeeId, int teamId) {
        try {
            Employee employee = employeeRepository.findById(employeeId).orElse(null);
            Teams team = teamsRepository.findById(teamId).orElse(null);
            if (employee != null && team != null) {
                team.addEmployee(employee);
                teamsRepository.save(team);
            }
        } catch (Exception e) {
            throw new CustomException("Failed to add employee to team", e);
        }
    }
}
