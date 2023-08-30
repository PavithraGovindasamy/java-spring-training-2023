package com.sirius.springenablement.meetingScheduler.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sirius.springenablement.meetingScheduler.entity.Rooms;
import jakarta.persistence.Column;

import java.util.List;

/**
 *  class for representing room information in responses.
 */
public class RoomsResponseDto {
    private Integer id;

    private String roomName;

    private Integer roomCapacity;


    /**
     * Default constructor
     */
    public RoomsResponseDto() {

    }

    /**
     * constructor for the RoomsResponseDto class.
     *
     * @param id           The ID of the room.
     * @param roomName     The name of the room.
     * @param roomCapacity The capacity of the room.
     */
    public RoomsResponseDto(Integer id, String roomName, Integer roomCapacity) {
        this.id = id;
        this.roomName = roomName;
        this.roomCapacity = roomCapacity;
    }

    /**
     * Get the ID of the room.
     *
     * @return The ID of the room.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set the ID of the room.
     *
     * @param id The ID to set for the room.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Get the name of the room.
     *
     * @return The name of the room.
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * Set the name of the room.
     *
     * @param roomName The name to set for the room.
     */
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    /**
     * Get the capacity of the room.
     *
     * @return The capacity of the room.
     */
    public Integer getRoomCapacity() {
        return roomCapacity;
    }

    /**
     * Set the capacity of the room.
     *
     * @param roomCapacity The capacity to set for the room.
     */
    public void setRoomCapacity(Integer roomCapacity) {
        this.roomCapacity = roomCapacity;
    }



    /**
     * Generate a string representation of the RoomsResponseDto object.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "RoomsResponseDto{" +
                "id=" + id +
                ", roomName='" + roomName + '\'' +
                ", roomCapacity=" + roomCapacity +
                '}';
    }
}
