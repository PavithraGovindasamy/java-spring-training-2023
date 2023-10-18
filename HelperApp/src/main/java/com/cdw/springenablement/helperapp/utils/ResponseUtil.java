package com.cdw.springenablement.helperapp.utils;
import com.cdw.springenablement.helperapp.client.models.ApiResponseDto;
import com.cdw.springenablement.helperapp.client.models.RegisterDto;
import com.cdw.springenablement.helperapp.client.models.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Class that constructs objects for response objects
 */
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

    public static ResponseEntity<RegisterDto> generateSuccessResponse(String message, Long id, UserDto userDto) {
        RegisterDto registerDto = new RegisterDto();
        String completeMessage = message + " User ID: " + id;
        registerDto.setStatusCode((long) HttpStatus.OK.value());
        registerDto.setMessage(completeMessage);
        registerDto.setFirstName(userDto.getFirstName());
        registerDto.setLastName(userDto.getLastName());
        registerDto.setDateOfBirth(userDto.getDateOfBirth());
        registerDto.setGender(userDto.getGender());
        registerDto.setEmail(userDto.getEmail());

        return ResponseEntity.ok(registerDto);
    }

    public static ResponseEntity<ApiResponseDto> generateSuccessResponse(String message, List<Long> userIds) {
        String completeMessage = message + " User IDs: " + userIds;
        ApiResponseDto response = new ApiResponseDto()
                .message(completeMessage)
                .statusCode((long) HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }



}