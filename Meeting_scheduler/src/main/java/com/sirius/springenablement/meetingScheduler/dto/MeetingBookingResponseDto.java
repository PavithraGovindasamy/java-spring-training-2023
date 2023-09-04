package com.sirius.springenablement.meetingScheduler.dto;


/**
 * class that stores the response object message
 */
public class MeetingBookingResponseDto {

    private boolean success;
    private String message;



    /**
     * @param success
     * @param message
     */
    public MeetingBookingResponseDto(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    /**
     * '
     * Empty constructor
     */
    public MeetingBookingResponseDto() {

    }

    /**
     * It returns whether the response is succsess or not
     *
     * @return
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Method which set whether its success or not
     *
     * @param success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Method which returns the message as response
     *
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * Method which sets the message for a response
     *
     * @param message
     */

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Method which sets the Booked Team
     */

    public void setBookedTeam(String teamName) {
    }

}
