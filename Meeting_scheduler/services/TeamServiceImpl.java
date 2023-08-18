package com.sirius.springenablement.demo.services;
import com.sirius.springenablement.demo.entity.Employee;
import com.sirius.springenablement.demo.repository.EmployeeRepository;
import com.sirius.springenablement.demo.repository.TeamsRepository;
import com.sirius.springenablement.demo.entity.Teams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * Service that implements the TeamService implementation -basic crud operations
 */
@Service
public class TeamServiceImpl implements TeamService{
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
        System.out.println("Teams"+teams);
        return teamsRepository.save(teams);
    }

    /**
     * Method which fetches a particular team
     * @param teamId
     * @return
     */
    @Override
    public Optional<Teams> findById(int teamId) {
        return teamsRepository.findById(teamId);
    }

    /**
     * Method which deletes a team
     * @param teamId
     */

    @Override
    public void deleteById(int teamId) {
        teamsRepository.deleteById(teamId);
    }

    /**
     * Method which adds Employee to Team
     * @param employeeId
     * @param teamId
     */
    @Override
    public void addEmployeeToTeam(int employeeId, int teamId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
      Teams team = teamsRepository.findById(teamId).orElse(null);
            team.addEmployee(employee);
            teamsRepository.save(team);

    }



}
