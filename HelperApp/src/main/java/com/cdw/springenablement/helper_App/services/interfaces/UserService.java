package com.cdw.springenablement.helper_App.services.interfaces;

import com.cdw.springenablement.helper_App.client.models.BookingTechnicianDto;
import com.cdw.springenablement.helper_App.client.models.HelperAppointmentDto;
import com.cdw.springenablement.helper_App.client.models.TimeSlotDto;
import com.cdw.springenablement.helper_App.client.models.UserDto;

import java.util.List;

/**
 * Interface which has methods of AdminService
 */

public  interface UserService {
    Long registerUser(UserDto userDto) ;
    void bookTechnician(BookingTechnicianDto bookingTechnicianDto) ;
    List<HelperAppointmentDto> getAppointment(Long helperId);
    List<TimeSlotDto> getAvailableTechnicians() ;
    void updateHelperSpecialization(Long id, String specialisation) ;
}