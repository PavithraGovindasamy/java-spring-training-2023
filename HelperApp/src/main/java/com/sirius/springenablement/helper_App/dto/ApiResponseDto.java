package com.sirius.springenablement.helper_App.dto;
import lombok.Data;
import lombok.AllArgsConstructor;
@Data
@AllArgsConstructor
@lombok.NoArgsConstructor
public class ApiResponseDto {

    private String message;
    private int statusCode;


    public ApiResponseDto(String s) {
        message=s;
    }
}
