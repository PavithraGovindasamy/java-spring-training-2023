package com.sirius.springenablement.ticket_booking.controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import com.sirius.springenablement.ticket_booking.entity.Locations;
import com.sirius.springenablement.ticket_booking.dto.UserDto;
import com.sirius.springenablement.ticket_booking.entity.Users;
import  com.sirius.springenablement.ticket_booking.services.UserServiceImpl;
import org.springframework.http.HttpStatus;
import com.sirius.springenablement.ticket_booking.dto.ApiResponseDto;
import com.sirius.springenablement.ticket_booking.dto.*;
import java.util.List;
import java.util.stream.Collectors;
import com.sirius.springenablement.ticket_booking.services.BookingService;
import com.sirius.springenablement.ticket_booking.services.JwtService;
import com.sirius.springenablement.ticket_booking.entity.Bookings;
import  com.sirius.springenablement.ticket_booking.repository.BookingRepository;
import com.sirius.springenablement.ticket_booking.repository.LocationsRepository;
import org.springframework.security.core.context.SecurityContextHolder;
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingRepository bookingRepository;


    @Autowired
    private LocationsRepository locationsRepository;

    @Autowired
    private com.sirius.springenablement.ticket_booking.repository.UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDto> registerUser(@RequestBody UserDto userDto) {
        try {
            userService.registerUser(userDto);

            ApiResponseDto response = new ApiResponseDto("User registered successfully.", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponseDto response = new ApiResponseDto("Registration failed: " + e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    @Autowired
    private JwtService jwtService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<BookingResponseDto>> getUserBookings(@PathVariable Long userId) {
        System.out.println("nm,"+ userId);
        List<Bookings> userBookings = bookingService.getUserBookings(userId);
        // Convert booking entities to DTOs and return them in the response
        List<BookingResponseDto> bookingResponseDtoList = userBookings.stream()
                .map(booking -> {
                    BookingResponseDto dto = new BookingResponseDto();
                    dto.setBookingId(booking.getId());
                    dto.setUserId((long) booking.getUser().getId());
                    dto.setMovieName(booking.getShow().getMovieName());
                    dto.setNoOfTickets(booking.getNoOfTickets());
                    dto.setTheatreName(booking.getShow().getTheatre().getName());
                    dto.setLocation(booking.getShow().getTheatre().getLocation().getName());
                    dto.setStatus(booking.getBookingStatus());


                    return dto;


                })
                .collect(Collectors.toList());
        System.out.println(bookingResponseDtoList);
        return ResponseEntity.ok(bookingResponseDtoList);
    }




        @PostMapping("/book")
        public ResponseEntity<BookingResponseDto> bookTicket(@RequestBody BookingRequestDto bookingRequestDto) {
            try {
                Locations selectedLocation = locationsRepository.getLocationByName(bookingRequestDto.getLocation());
                System.out.println("Location added"+selectedLocation);
                if(!selectedLocation.isPrime()) {

                    BookingResponseDto bookingResponseDto = bookingService.bookTicket(bookingRequestDto);
                    return ResponseEntity.ok(bookingResponseDto);
                }
                else {
                    BookingResponseDto bookingResponseDto =new BookingResponseDto();
                    bookingResponseDto.setStatus("Users cannot book prime locations");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(bookingResponseDto);
                }
            } catch (Exception e) {
                BookingResponseDto bookingResponseDto =new BookingResponseDto();
                bookingResponseDto.setStatus("false-No- tickets available");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(bookingResponseDto);
            }
        }

    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<String> requestCancellation(@PathVariable Long bookingId) {
        try {

            bookingService.requestCancellation(bookingId);
            return ResponseEntity.ok("Cancellation request submitted successfully");
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/updateTicketCount")
    public ResponseEntity<ApiResponseDto> updateTicketCount(@RequestBody UpdateTicketCountRequestDto requestDto) {
        try {
            Users user = userRepository.findById(requestDto.getUserId())
                    .orElseThrow(() -> new Exception("User not found"));
            Bookings booking = bookingRepository.findById(requestDto.getBookingId())
                    .orElseThrow(() -> new Exception("Booking not found"));
            if(booking.getBookingStatus().equals("Confirmed")) {
                 System.out.println("booking" + booking.getBookingStatus()+"id"+booking.getId());
                int currentTicketCount = booking.getNoOfTickets();
                int newTicketCount;

                if ("increase".equalsIgnoreCase(requestDto.getAction())) {
                    newTicketCount = currentTicketCount + requestDto.getNewTicketCount();
                } else if ("decrease".equalsIgnoreCase(requestDto.getAction())) {
                    newTicketCount = currentTicketCount - requestDto.getNewTicketCount();
                    if (newTicketCount < 0) {
                        throw new Exception("Ticket count");
                    }
                } else {
                    throw new Exception("Invalid action");
                }


                int availableTicketCount = booking.getShow().getAvailableCount();
                if (newTicketCount < 0 || newTicketCount > availableTicketCount) {
                    throw new Exception("Invalid ticket count");
                }


                booking.setNoOfTickets(newTicketCount);
                bookingRepository.save(booking);


                ApiResponseDto response = new ApiResponseDto("Ticket count updated successfully.", HttpStatus.OK.value());
                return ResponseEntity.ok(response);
            }
            else{
                ApiResponseDto response = new ApiResponseDto("Your ticket has been cancelled.");
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            ApiResponseDto response = new ApiResponseDto("Ticket count update failed: " + e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }



    @PostMapping("/logout")
    public void logout() {

        SecurityContextHolder.clearContext();
    }









}


