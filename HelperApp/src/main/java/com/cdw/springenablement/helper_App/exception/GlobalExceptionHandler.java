package com.cdw.springenablement.helper_App.exception;

import com.cdw.springenablement.helper_App.client.models.ApiResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * class that handles exceptiom globally
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HelperAppException.class)
    public ResponseEntity<ApiResponseDto> handleCustomException(HelperAppException ex) {
        ApiResponseDto response = new ApiResponseDto()
                .message(ex.getMessage())
                .statusCode((long) ex.getHttpStatus().value());
        return ResponseEntity.status(ex.getHttpStatus()).body(response);
    }

}
