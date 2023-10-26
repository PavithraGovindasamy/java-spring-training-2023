package com.cdw.springenablement.helperapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * class which gets the message and status code and sends as a response
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class HelperAppException extends RuntimeException {
    private final HttpStatus httpStatus;

    public HelperAppException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }

    public HelperAppException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
