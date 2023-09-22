package com.sirius.springenablement.ticket_booking.controller;
import com.sirius.springenablement.ticket_booking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import  com.sirius.springenablement.ticket_booking.entity.*;
import  java.util.*;
import com.sirius.springenablement.ticket_booking.dto.ApiResponseDto;
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
    @Autowired
    private UserRepository userRepository;
    @GetMapping
    public ResponseEntity<List<CancellationRequest>> cancel(){
       List<CancellationRequest> bookings=bookingService.getCancellationRequest();
       return ResponseEntity.ok(bookings);
    }


    @PostMapping
    public ResponseEntity<ApiResponseDto> cancelTickets(){
        List<CancellationRequest> bookings=bookingService.getCancellationRequest();
        bookingService.cancelTickets(bookings);
        ApiResponseDto bookingResponseDto =new ApiResponseDto();
        bookingResponseDto.setMessage("Booking Cancelled");
        bookingResponseDto.setStatusCode(200);
        return ResponseEntity.ok(bookingResponseDto);

    }



    @PostMapping("/book")
    public ResponseEntity<BookingResponseDto> bookTicket(@RequestBody BookingRequestDto bookingRequestDto) {
        try {
            Users user=userRepository.findById(bookingRequestDto.getUserId()).orElse(null);
            boolean hasUserRole = user.getRoles()
                    .stream()
                    .anyMatch(role -> "ROLE_USER".equals(role.getName()));
            if(!hasUserRole) {

                BookingResponseDto bookingResponseDto = bookingService.bookTicket(bookingRequestDto);
                return ResponseEntity.ok(bookingResponseDto);
            }else{
                BookingResponseDto bookingResponseDto = new BookingResponseDto();
                bookingResponseDto.setStatus("You can only book tickets to user");
                return ResponseEntity.ok(bookingResponseDto);

            }
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
