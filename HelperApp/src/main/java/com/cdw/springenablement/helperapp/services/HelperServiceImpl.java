package com.cdw.springenablement.helperapp.services;

import com.cdw.springenablement.helperapp.client.models.HelperAppointmentDto;
import com.cdw.springenablement.helperapp.entity.Bookings;
import com.cdw.springenablement.helperapp.entity.Helper;
import com.cdw.springenablement.helperapp.entity.Users;
import com.cdw.springenablement.helperapp.exception.HelperAppException;
import com.cdw.springenablement.helperapp.repository.*;
import com.cdw.springenablement.helperapp.services.interfaces.HelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HelperServiceImpl implements HelperService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;


    @Autowired
    private HelperRepository helperRepository;

    /**
     * Retrieves a list of appointments for a specified helper.
     */

    @Override
    public List<HelperAppointmentDto> getAppointment() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Users> newUser = userRepository.findByEmail(authentication.getName());
        Long helperId = newUser.get().getId();
        Helper helpers = helperRepository.findByUserId(helperId);
        Helper helper = helperRepository.findById(helpers.getId()).orElse(null);
        Long userId = helper.getUser().getId();
        Users users = userRepository.findById((long) userId).orElse(null);
        List<HelperAppointmentDto> appointmentDtos = null;
        if (helper != null) {
            List<Bookings> bookings = bookingRepository.findByHelperId(helpers.getId());
            if(bookings.isEmpty()){
                throw new HelperAppException("No Appointments found for the helper");
            }
            appointmentDtos = bookings.stream()
                    .map(booking -> {
                        HelperAppointmentDto dto = new HelperAppointmentDto();
                        dto.setAppointmentId(booking.getId());
                        dto.setStartTime(String.valueOf(booking.getTimeSlot().getStartTime()));
                        dto.setEndTime(String.valueOf(booking.getTimeSlot().getEndTime()));
                        dto.setCustomerName(booking.getUsers().getFirstName() + " " + booking.getUsers().getLastName());
                        dto.setCustomerEmail(booking.getUsers().getEmail());
                        return dto;
                    })
                    .collect(Collectors.toList());

        }
        return appointmentDtos;
    }
}
