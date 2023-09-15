package com.sirius.springenablement.ticket_booking.services;
import org.springframework.beans.factory.annotation.*;
import com.sirius.springenablement.ticket_booking.repository.*;
import com.sirius.springenablement.ticket_booking.dto.*;
import com.sirius.springenablement.ticket_booking.entity.*;
@org.springframework.stereotype.Service
public class BookingService {

    @Autowired
    private ShowService showService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public BookingResponseDto bookTicket(BookingRequestDto bookingRequestDto) throws Exception {
        Users user = userRepository.findById(bookingRequestDto.getUserId())
                .orElseThrow(() -> new Exception("User not found"));
Shows show=null;

//        Shows show = showService.findAvailableShows(
//
//                bookingRequestDto.getTimeSlot(),
//
//                bookingRequestDto.getTimeSlot()
//        );


        if (show == null) {
            throw new Exception("Show not available");
        }

        Bookings booking = new Bookings();
        booking.setUser(user);
        booking.setShow(show);
        booking.setBookingStatus("Confirmed");

        Bookings savedBooking = bookingRepository.save(booking);


        BookingResponseDto bookingResponseDto = new BookingResponseDto();
        bookingResponseDto.setBookingId(savedBooking.getId());
        bookingResponseDto.setStatus(savedBooking.getBookingStatus());

        return bookingResponseDto;
    }
}
