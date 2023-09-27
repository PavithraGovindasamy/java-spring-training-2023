package com.sirius.springenablement.helper_App.dto;
import org.springframework.http.HttpStatus;

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
