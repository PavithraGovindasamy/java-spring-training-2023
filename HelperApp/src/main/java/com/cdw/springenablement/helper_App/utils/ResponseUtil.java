package com.cdw.springenablement.helper_App.utils;
import com.cdw.springenablement.helper_App.client.models.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
    public static ResponseEntity<ApiResponseDto> generateSuccessResponse(String message) {
        ApiResponseDto response = new ApiResponseDto()
                .message(message)
                .statusCode((long) HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<ApiResponseDto> generateSuccessResponse(String message, Long id) {
        String completeMessage = message + " User ID: " + id;
        ApiResponseDto response = new ApiResponseDto()
                .message(completeMessage)
                .statusCode((long) HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }


    public static ResponseEntity<ApiResponseDto> generateErrorResponse(String errorMessage, HttpStatus badRequest) {
        ApiResponseDto response = new ApiResponseDto()
                .message(errorMessage)
                .statusCode((long) HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.ok(response);
    }
}