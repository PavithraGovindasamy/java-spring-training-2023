package com.cdw.springenablement.helperapp.exception;

import org.springframework.http.HttpStatus;

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
