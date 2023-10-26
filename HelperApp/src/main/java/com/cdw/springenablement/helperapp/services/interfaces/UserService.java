package com.cdw.springenablement.helperapp.services.interfaces;

import com.cdw.springenablement.helperapp.client.models.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface which has methods of UserService
 */

public  interface UserService {
    Long registerUser(UserDto userDto) ;
    void bookTechnician(BookingTechnicianDto bookingTechnicianDto) ;

    void updateHelperSpecialization(Long id, String specialisation) ;

    List<BookingDto> getUserBookings();

}