package com.cdw.springenablement.helperapp.exception;
import com.cdw.springenablement.helperapp.client.models.ApiResponseDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<ValidationError>> handleValidationException(ConstraintViolationException ex) {
        List<ValidationError> errors = new ArrayList<>();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            ValidationError error = new ValidationError(
                    violation.getPropertyPath().toString(),
                    violation.getMessage()
            );
            errors.add(error);
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }






}











