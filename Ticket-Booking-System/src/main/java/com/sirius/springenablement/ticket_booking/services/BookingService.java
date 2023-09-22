package com.sirius.springenablement.ticket_booking.services;
import org.springframework.beans.factory.annotation.*;
import com.sirius.springenablement.ticket_booking.repository.*;
import com.sirius.springenablement.ticket_booking.dto.*;
import com.sirius.springenablement.ticket_booking.entity.*;
import java.util.List;
import com.sirius.springenablement.ticket_booking.entity.CancellationRequest;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    @Autowired
    private ShowService showService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CancellationRequestRepository cancellationRequestRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ShowsRepository showsRepository;


    public BookingResponseDto bookTicket(BookingRequestDto bookingRequestDto) throws Exception {


            Users user = userRepository.findById(bookingRequestDto.getUserId())
                    .orElseThrow(() -> new Exception("User not found"));
            Shows show = showsRepository.findByMovieNameAndTheatreName(bookingRequestDto.getMovieName(), bookingRequestDto.getTheatreName());


            if (show == null) {
                throw new Exception("Show not available");
            }
            int numberOfTicketsToBook = bookingRequestDto.getNoOfTickets();


            // Reducing the ticket count
            int id = show.getId();

            Bookings booking = new Bookings();
            booking.setUser(user);
            booking.setShow(show);
            booking.setBookingStatus("Confirmed");
            booking.setNoOfTickets(bookingRequestDto.getNoOfTickets());


            Bookings savedBooking = bookingRepository.save(booking);

            int newAvailableCount = show.getAvailableCount() - numberOfTicketsToBook;
            show.setAvailableCount(newAvailableCount);
            showsRepository.save(show);

            LocalDate now = LocalDate.now();
            BookingResponseDto bookingResponseDto = new BookingResponseDto();
            bookingResponseDto.setBookingId(savedBooking.getId());
            bookingResponseDto.setUserId(bookingRequestDto.getUserId());
            bookingResponseDto.setMovieName(bookingRequestDto.getMovieName());
            bookingResponseDto.setNoOfTickets(bookingRequestDto.getNoOfTickets());
            bookingResponseDto.setStatus(savedBooking.getBookingStatus());
            bookingResponseDto.setLocation(bookingRequestDto.getLocation());
            bookingResponseDto.setTheatreName(bookingRequestDto.getTheatreName());
            return bookingResponseDto;
    }


    // get user booking
    public List<Bookings> getUserBookings(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public void requestCancellation(Long bookingId) throws Exception {
        Bookings booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking == null) {
            throw new Exception("Booking not found");
        }
        booking.setBookingStatus("Cancellation Requested");
        CancellationRequest cancellationRequest=new CancellationRequest();
        cancellationRequest.setBookingId(bookingId);
        cancellationRequest.setUserId((long) booking.getUser().getId());
        cancellationRequestRepository.save(cancellationRequest);



    }


    public List<CancellationRequest> getCancellationRequest() {
        List<CancellationRequest> bookings=cancellationRequestRepository.findAll();

        return bookings;
    }
    public void cancelShow(long showId) {
        Shows show = showsRepository.findById(showId).orElse(null);
        show.setActive(false);
        showsRepository.save(show);
    }


    public void cancelTickets(List<CancellationRequest> bookings) {
        bookings.forEach(cancellationRequest -> {
            Bookings cancelBookings=bookingRepository.findById(cancellationRequest.getBookingId()).orElse(null);
            int ticketCount =cancelBookings.getShow().getAvailableCount();
            Shows show=cancelBookings.getShow();


            show.setAvailableCount(show.getAvailableCount()+ticketCount);


            showsRepository.save(show);
            cancelBookings.setBookingStatus("cancelled");
            bookingRepository.save(cancelBookings);
        });


        cancellationRequestRepository.deleteAll(bookings);

    }
}
