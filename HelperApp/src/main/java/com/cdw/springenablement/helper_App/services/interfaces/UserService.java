package com.cdw.springenablement.helper_App.services.interfaces;

import com.cdw.springenablement.helper_App.client.models.BookingTechnicianDto;
import com.cdw.springenablement.helper_App.client.models.HelperAppointmentDto;
import com.cdw.springenablement.helper_App.client.models.TimeSlotDto;
import com.cdw.springenablement.helper_App.client.models.UserDto;

import java.time.LocalDate;
import java.util.List;

public  interface UserService {



    int registerUser(UserDto userDto) throws Exception;

      void bookTechnician(BookingTechnicianDto bookingTechnicianDto) throws Exception;

    List<HelperAppointmentDto> getAppointment(Long helperId);
    List<TimeSlotDto> getAvailableTechnicians(LocalDate date, String profession, String startTime, String endTime) throws Exception;

    void updateHelperSpecialization(int id, String specialisation) throws Exception;
}