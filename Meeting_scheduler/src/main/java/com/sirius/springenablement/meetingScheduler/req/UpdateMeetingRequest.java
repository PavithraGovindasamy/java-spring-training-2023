package com.sirius.springenablement.meetingScheduler.req;

import com.sirius.springenablement.meetingScheduler.entity.TimeSlot;

import java.util.List;

/**
 * class which stores all the info
 */
public class UpdateMeetingRequest {
    private int timeSlotId;
    private TimeSlot bookingRequest;
    private String roomName;
    private List<Integer> addedEmployees;
    private List<Integer> removedEmployees;

    public UpdateMeetingRequest(int timeSlotId, TimeSlot bookingRequest, String roomName, List<Integer> addedEmployees, List<Integer> removedEmployees) {
        this.timeSlotId = timeSlotId;
        this.bookingRequest = bookingRequest;
        this.roomName = roomName;
        this.addedEmployees = addedEmployees;
        this.removedEmployees = removedEmployees;
    }

    public UpdateMeetingRequest() {

    }

    public int getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(int timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public TimeSlot getBookingRequest() {
        return bookingRequest;
    }

    public void setBookingRequest(TimeSlot bookingRequest) {
        this.bookingRequest = bookingRequest;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public List<Integer> getAddedEmployees() {
        return addedEmployees;
    }

    public void setAddedEmployees(List<Integer> addedEmployees) {
        this.addedEmployees = addedEmployees;
    }

    public List<Integer> getRemovedEmployees() {
        return removedEmployees;
    }

    public void setRemovedEmployees(List<Integer> removedEmployees) {
        this.removedEmployees = removedEmployees;
    }
}
