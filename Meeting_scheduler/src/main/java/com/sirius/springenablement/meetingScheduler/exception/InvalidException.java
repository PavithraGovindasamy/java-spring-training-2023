package com.sirius.springenablement.meetingScheduler.exception;

/**
 * class which handles the c
 */
public class InvalidException extends RuntimeException {

    public InvalidException(String message) {
        super(message);
    }

    public InvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}
