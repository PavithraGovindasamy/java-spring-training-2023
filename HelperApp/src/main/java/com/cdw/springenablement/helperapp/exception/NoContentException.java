package com.cdw.springenablement.helperapp.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class NoContentException extends RuntimeException {
    private final HttpStatus httpStatus;

    public NoContentException(String message) {
        this(message, HttpStatus.NO_CONTENT);
    }

    public NoContentException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
