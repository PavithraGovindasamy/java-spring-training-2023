package com.cdw.springenablement.helperapp.exception;

import com.cdw.springenablement.helperapp.client.models.ApiResponseDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * GlobalExceptionHandler class handles exceptions globally for the application.
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles custom exceptions thrown within the application.
     * @param ex
     * @return ResponseEntity containing the custom error response
     */


    @ExceptionHandler(HelperAppException.class)
    public ResponseEntity<ApiResponseDto> handleCustomException(HelperAppException ex) {
        ApiResponseDto response = new ApiResponseDto()
                .message(ex.getMessage())
                .statusCode((long) ex.getHttpStatus().value());
        return ResponseEntity.status(ex.getHttpStatus()).body(response);
    }


    /**
     * Handles validation exceptions caused by  bean validations.
     *
     * @param ex
     * @return ResponseEntity containing a list of validation errors .
     */


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


    /**
     * Handles method argument validation errors caused by the  {@Valid} annotation.
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return ResponseEntity containing a list of validation errors
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<ValidationError> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            ValidationError validationError = new ValidationError(error.getField(), error.getDefaultMessage());
            errors.add(validationError);
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
