package com.sirius.springenablement.meetingScheduler.dto;

import java.util.List;

/**
 * class that stores the response object message
 */
public class AvailableRoomsResponseDto {
    private boolean success;
    private String message;
    private RoomsResponseDto roomResponseDto;

    /**
     *
     * @param success
     * @param message
     */
    public AvailableRoomsResponseDto(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    /**'
     * Empty constructor
     */
    public AvailableRoomsResponseDto() {

    }

    /**
     * It returns whether the response is succsess or not
     * @return
     */

    public boolean isSuccess() {
        return success;
    }

    /**
     * Method which set whether its success or not
     * @param success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Method which returns the message as response
     * @return
     */

    public String getMessage() {
        return message;
    }

    /**
     * Method which sets the message for a response
     * @param message
     */

    public void setMessage(String message) {
        this.message = message;
    }
    /**
     * Method which returns the roomResponse
     */

    public RoomsResponseDto getRoomResponseDto() {
        return roomResponseDto;
    }

    /**
     * Method which set the message
     * @param roomResponseDto
     */

    public void setAvailableRoom(RoomsResponseDto roomResponseDto) {
        this.roomResponseDto=roomResponseDto;
    }
}
