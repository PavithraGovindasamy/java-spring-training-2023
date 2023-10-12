package com.cdw.springenablement.helper_App.services.interfaces;

import com.cdw.springenablement.helper_App.client.models.*;

import java.util.List;

/**
 * Interface which has methods of AdminService
 */

public  interface UserService {
    Long registerUser(UserDto userDto) ;
    void bookTechnician(BookingTechnicianDto bookingTechnicianDto) ;
    List<HelperAppointmentDto> getAppointment();
    List<TimeSlotDto> getAvailableTechnicians() ;
    void updateHelperSpecialization(Long id, String specialisation) ;

    List<BookingDto> getUserBookings();
}