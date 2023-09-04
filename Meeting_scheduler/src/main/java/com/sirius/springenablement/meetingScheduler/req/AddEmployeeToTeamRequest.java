package com.sirius.springenablement.meetingScheduler.req;

/**
 * class that stores the id for requesting the object
 */
public class AddEmployeeToTeamRequest {
    private int teamId;
    private int employeeId;


    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public AddEmployeeToTeamRequest(int teamId, int employeeId) {
        this.teamId = teamId;
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "AddEmployeeToTeamRequest{" +
                "teamId=" + teamId +
                ", employeeId=" + employeeId +
                '}';
    }


}
