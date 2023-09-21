package com.sirius.springenablement.ticket_booking.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import  com.sirius.springenablement.ticket_booking.entity.*;
import  java.util.*;
import org.springframework.web.bind.annotation.RestController;
import  org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import  com.sirius.springenablement.ticket_booking.dto.BookingRequestDto;
import  com.sirius.springenablement.ticket_booking.dto.BookingResponseDto;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.sirius.springenablement.ticket_booking.services.BookingService;

@RestController
@RequestMapping("/business")
public class BusinessController {
    @Autowired
    private BookingService bookingService;
    @GetMapping
    public ResponseEntity<List<CancellationRequest>> cancel(){
       List<CancellationRequest> bookings=bookingService.getCancellationRequest();
       return ResponseEntity.ok(bookings);
    }




    @PostMapping("/book")
    public ResponseEntity<BookingResponseDto> bookTicket(@RequestBody BookingRequestDto bookingRequestDto) {
        try {
            BookingResponseDto bookingResponseDto = bookingService.bookTicket(bookingRequestDto);
            return ResponseEntity.ok(bookingResponseDto);
        } catch (Exception e) {
            BookingResponseDto bookingResponseDto =new BookingResponseDto();
            bookingResponseDto.setStatus("false-No- tickets available");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(bookingResponseDto);
        }
    }

    @PutMapping("/{showId}")
    public void cancelShow(@PathVariable Long showId){
        try{
            bookingService.cancelShow(showId);
            BookingResponseDto bookingResponseDto=new BookingResponseDto();
            bookingResponseDto.setStatus("Show cancelled");


        }
        catch (Exception e) {
            BookingResponseDto bookingResponseDto =new BookingResponseDto();
            bookingResponseDto.setStatus("Could not cancel shows");
        }

    }

}
