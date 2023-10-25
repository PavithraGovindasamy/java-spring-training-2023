package com.cdw.springenablement.helperapp.scheduler;
import com.cdw.springenablement.helperapp.entity.Bookings;
import com.cdw.springenablement.helperapp.repository.BookingRepository;
import com.cdw.springenablement.helperapp.services.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * class that scheduled for every 1month to delete the bookings which are inactive
 */
@Component
@Slf4j
public class HelperCron {


    @Autowired
    private JwtService jwtService;

    @Autowired
    private BookingRepository bookingRepository;


    @Scheduled(cron = "0 0 0 1 * *")
    public void scheduler() throws InterruptedException {
        List<Bookings>  bookings=bookingRepository.findByActiveIsFalse();
        bookingRepository.deleteAll(bookings);
    }



}

