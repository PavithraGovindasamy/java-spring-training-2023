package com.sirius.springenablement.demo.services;

import com.sirius.springenablement.demo.entity.Employee;
import com.sirius.springenablement.demo.repository.EmployeeRepository;
import com.sirius.springenablement.demo.repository.TeamsRepository;
import com.sirius.springenablement.demo.entity.Teams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class TeamServiceImpl implements TeamService{
    @Autowired
    public TeamsRepository teamsRepository;

    @Autowired
    public EmployeeRepository employeeRepository;

    @Override
    public Teams save(Teams teams) {
        System.out.println("Teams"+teams);
        return teamsRepository.save(teams);
    }

    @Override
    public Optional<Teams> findById(int teamId) {
        return teamsRepository.findById(teamId);
    }

    @Override
    public void deleteById(int teamId) {
        teamsRepository.deleteById(teamId);
    }

    @Override
    public void addEmployeeToTeam(int employeeId, int teamId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
      Teams team = teamsRepository.findById(teamId).orElse(null);


            team.addEmployee(employee);
            teamsRepository.save(team);

    }



}
