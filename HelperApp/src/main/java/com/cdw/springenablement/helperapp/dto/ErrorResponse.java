package com.cdw.springenablement.helperapp.dto;
import org.springframework.http.HttpStatus;

/**
 * class that gets statusCode,message from jwtfilter and returns it
 * @Author pavithra
 */
@lombok.Getter
@lombok.Setter
@lombok.AllArgsConstructor
public class ErrorResponse {

    private HttpStatus statusCode;
    private String message;
    private Object body;

    public ErrorResponse(HttpStatus statusCode, String errorMessage){
        this(statusCode,errorMessage,errorMessage);
    }

    public int getStatusCodeValue() {
        return statusCode.value();
    }
}
