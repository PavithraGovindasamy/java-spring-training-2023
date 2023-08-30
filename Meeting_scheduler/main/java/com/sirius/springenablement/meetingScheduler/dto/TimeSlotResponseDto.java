package com.sirius.springenablement.meetingScheduler.dto;

import com.sirius.springenablement.meetingScheduler.entity.Rooms;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

/**
 * class for representing time slot information in responses.
 */
public class TimeSlotResponseDto {

    private int timeSlotId;
    private Date date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String description;
    private List<RoomsResponseDto> availableRoomsNow;

    /**
     * Default constructor
     */
    public TimeSlotResponseDto() {

    }

    /**
     *
     *
     * @param timeSlotId  The ID of the time slot.
     * @param date        The date of the time slot.
     * @param startTime   The start time of the time slot.
     * @param endTime     The end time of the time slot.
     * @param description The description of the time slot.
     */
    public TimeSlotResponseDto(int timeSlotId, Date date, LocalTime startTime, LocalTime endTime, String description) {
        this.timeSlotId = timeSlotId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }

    /**
     *
     * @return The ID of the time slot.
     */
    public int getTimeSlotId() {
        return timeSlotId;
    }

    /**
     *
     * @param timeSlotId The ID to set for the time slot.
     */
    public void setTimeSlotId(int timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    /**
     *
     * @return The date of the time slot.
     */
    public Date getDate() {
        return date;
    }

    /**
     *
     * @param date The date to set for the time slot.
     */

    public void setDate(Date date) {
        this.date = date;
    }

    /**
     *
     *
     * @return The start time of the time slot.
     */

    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     *
     *
     * @param startTime The start time to set for the time slot.
     */

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     *
     * @return The end time of the time slot.
     */

    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     *
     * @param endTime The end time to set for the time slot.
     */

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    /**
     *
     * @return The description of the time slot.
     */

    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description The description to set for the time slot.
     */

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return The list of available rooms.
     */

    public List<RoomsResponseDto> getAvailableRoomsNow() {
        return availableRoomsNow;
    }

    /**
     *
     * @param availableRoomsNow The list of available rooms to set.
     */

    public void setAvailableRooms(List<RoomsResponseDto> availableRoomsNow) {
        this.availableRoomsNow = availableRoomsNow;
    }

    /**
     *
     * @return A string representation of the object.
     */

    @Override
    public String toString() {
        return "TimeSlotResponseDto{" +
                "timeSlotId=" + timeSlotId +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", description='" + description + '\'' +
                '}';
    }
}
