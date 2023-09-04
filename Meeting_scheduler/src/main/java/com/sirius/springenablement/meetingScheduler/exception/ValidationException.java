package com.sirius.springenablement.meetingScheduler.exception;

/**
 * Class which handles the validation exception
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
