package com.cdw.springenablement.helperapp.services.interfaces;

import com.cdw.springenablement.helperapp.client.models.*;

import java.util.List;

/**
 * Interface which has methods of AdminService
 */

public  interface UserService {
    Long registerUser(UserDto userDto) ;
    void bookTechnician(BookingTechnicianDto bookingTechnicianDto) ;

    List<TimeSlotDto> getAvailableTechnicians() ;
    void updateHelperSpecialization(Long id, String specialisation) ;

    List<BookingDto> getUserBookings();
}