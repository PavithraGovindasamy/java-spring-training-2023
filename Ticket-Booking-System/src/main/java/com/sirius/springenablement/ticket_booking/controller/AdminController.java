package com.sirius.springenablement.ticket_booking.controller;
import  org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
 import org.springframework.web.bind.annotation.RequestBody;
 import org.springframework.web.bind.annotation.PathVariable;
import com.sirius.springenablement.ticket_booking.services.TheatreService;
import org.springframework.http.HttpStatus;
import com.sirius.springenablement.ticket_booking.dto.ApiResponseDto;
import com.sirius.springenablement.ticket_booking.entity.Theatre;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private TheatreService theatreService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponseDto> addLocation(@RequestBody Theatre requestDto) {
        try {

            theatreService.addTheatre(requestDto);
            ApiResponseDto responseDto=new ApiResponseDto();
            responseDto.setStatusCode(200);
            responseDto.setMessage("Theatre added successfully");

            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {

            ApiResponseDto responseDto=new ApiResponseDto();
            responseDto.setStatusCode(500);
            responseDto.setMessage("Failed to add theatre");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }


    @DeleteMapping("/remove/{theatreId}")
    public ResponseEntity<String> removeLocation(@PathVariable Long theatreId) {
        try {
            theatreService.removeTheatre(theatreId);
            return ResponseEntity.ok("Theatre removed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to remove theatre: " + e.getMessage());
        }
    }
}
