package com.sirius.springenablement.meetingScheduler.dto;

/**
 *  class for representing team information in responses.
 */
public class TeamsResponseDto {

private Integer teamId;

private String teamName;

private  Integer teamCapacity;

    /***
     * Constructor
     * @param teamId
     * @param teamName
     * @param teamCapacity
     */

    public TeamsResponseDto(Integer teamId,String teamName,Integer teamCapacity){
    this.teamId=teamId;
    this.teamName=teamName;
    this.teamCapacity=teamCapacity;
    }

    /**
     * Get the ID of the team.
     *
     * @return The ID of the team.
     */

    public Integer getTeamId() {
        return teamId;
    }

    /**
     * Set the ID of the room.
     *
     * @param  teamId->The ID to set for the team.
     */

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    /**
     * Returns the teamname
     * @return
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * Sets the teamname
     * @param teamName
     */

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    /**
     * Returns the team capacity
     * @return
     */

    public Integer getTeamCapacity() {
        return teamCapacity;
    }

    /**
     * sets the team capacity
     * @param teamCapacity
     */

    public void setTeamCapacity(Integer teamCapacity) {
        this.teamCapacity = teamCapacity;
    }

    @Override
    public String toString() {
        return "TeamsResponseDto{" +
                "teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                ", teamCapacity=" + teamCapacity +
                '}';
    }
}
