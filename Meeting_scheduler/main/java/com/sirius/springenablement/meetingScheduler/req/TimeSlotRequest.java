package com.sirius.springenablement.meetingScheduler.req;

import com.sirius.springenablement.meetingScheduler.entity.TimeSlot;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * class which stores various info for the request
 */
public class TimeSlotRequest {
    private TimeSlot timeSlot;

    private LocalDate date;
    private LocalTime start_time;
    private LocalTime end_time;
    private int employeeId;
    private String bookingType;
    private Integer teamId;
    private String roomName;
    private List<Integer> collaborators;

    public TimeSlotRequest() {

    }

    @Override
    public String toString() {
        return "TimeSlotRequest{" +
                "date='" + date + '\'' +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", employeeId=" + employeeId +
                ", bookingType='" + bookingType + '\'' +
                ", teamId=" + teamId +
                ", roomName='" + roomName + '\'' +
                ", collaborators=" + collaborators +
                '}';
    }

    public TimeSlotRequest(LocalDate date, LocalTime start_time, LocalTime end_time, int employeeId, String bookingType, Integer teamId, String roomName, List<Integer> collaborators) {
        this.date = date;
        this.start_time = start_time;
        this.end_time = end_time;
        this.employeeId = employeeId;
        this.bookingType = bookingType;
        this.teamId = teamId;
        this.roomName = roomName;
        this.collaborators = collaborators;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStart_time() {
        return start_time;
    }

    public void setStart_time(LocalTime start_time) {
        this.start_time = start_time;
    }

    public LocalTime getEnd_time() {
        return end_time;
    }

    public void setEnd_time(LocalTime end_time) {
        this.end_time = end_time;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public List<Integer> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<Integer> collaborators) {
        this.collaborators = collaborators;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

}
