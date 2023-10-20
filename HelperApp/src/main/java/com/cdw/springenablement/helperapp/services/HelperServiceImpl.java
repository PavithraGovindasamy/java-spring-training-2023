package com.cdw.springenablement.helperapp.services;

import com.cdw.springenablement.helperapp.client.models.HelperAppointmentDto;
import com.cdw.springenablement.helperapp.client.models.TimeSlotDto;
import com.cdw.springenablement.helperapp.client.models.TimeSlotDtos;
import com.cdw.springenablement.helperapp.constants.SuceessConstants;
import com.cdw.springenablement.helperapp.dto.TimeSlotDtoDisplay;
import com.cdw.springenablement.helperapp.entity.Bookings;
import com.cdw.springenablement.helperapp.entity.Helper;
import com.cdw.springenablement.helperapp.entity.TimeSlot;
import com.cdw.springenablement.helperapp.entity.Users;
import com.cdw.springenablement.helperapp.exception.HelperAppException;
import com.cdw.springenablement.helperapp.repository.*;
import com.cdw.springenablement.helperapp.services.interfaces.HelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @Autowired
    private TimeSlotRepository timeSlotRepository;

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


    /**
     * Method that gets all the timeslots
     * @return
     */

    public List<TimeSlotDtos> getAllTimeSlots() {
        List<TimeSlot> timeSlots = timeSlotRepository.findAll();
        List<TimeSlotDtos> timeSlotDtos = new ArrayList<>();
        for (TimeSlot timeSlot : timeSlots) {
            TimeSlotDtos timeSlotDto = new TimeSlotDtos();
            timeSlotDto.setId(timeSlot.getId());
            timeSlotDto.setStartTime(String.valueOf(timeSlot.getStartTime()));
            timeSlotDto.setEndTime(String.valueOf(timeSlot.getEndTime()));

            timeSlotDtos.add(timeSlotDto);
        }
        return timeSlotDtos;
    }


    /**
     * Retrieves available technicians for a user
     */

    @Override
    public List<TimeSlotDto> getAvailableTechnicians(LocalDate date, Long timeslotId) {
        LocalDate currentDate = LocalDate.now();
        boolean isFutureDate = date.isAfter(currentDate);
        boolean isWithinOneMonth = date.isBefore(currentDate.plusMonths(1));
        Boolean flag=false;
        if (date.isBefore(currentDate)) {
            throw new HelperAppException(SuceessConstants.PAST_DATE_NOT_ALLOWED);
        }
        if (isFutureDate && !isWithinOneMonth) {
            throw  new HelperAppException(SuceessConstants.INPUT_DATE);
        }
        List<TimeSlotDto> timeSlotDtos = new ArrayList<>();
        List<TimeSlot> timeSlots;
        if (timeslotId != null) {
            TimeSlot timeSlot = timeSlotRepository.findById(timeslotId)
                    .orElseThrow(() -> new HelperAppException(SuceessConstants.INVALID_TIMESLOT));
            List<Helper> availableHelpers = getAvailableHelpersForTimeSlot(date, timeSlot,flag);
            TimeSlotDto timeSlotDto = TimeSlotDtoDisplay.createTimeSlotDto(
                    timeSlot.getId(), timeSlot.getStartTime(), timeSlot.getEndTime(), date, availableHelpers);
            timeSlotDtos.add(timeSlotDto);
        } else {
            flag=true;
            timeSlots = timeSlotRepository.findAll();
            for (TimeSlot timeSlot : timeSlots) {
                List<Helper> availableHelpers = getAvailableHelpersForTimeSlot(date, timeSlot,flag);
                TimeSlotDto timeSlotDto = TimeSlotDtoDisplay.createTimeSlotDto(
                        timeSlot.getId(), timeSlot.getStartTime(), timeSlot.getEndTime(), date, availableHelpers);

                timeSlotDtos.add(timeSlotDto);
            }
        }

        return timeSlotDtos;
    }


    /**
     * Retrieves available helpers
     * @param date
     * @param timeSlot
     * @param flag
     * @return
     */


    public List<Helper> getAvailableHelpersForTimeSlot(LocalDate date, TimeSlot timeSlot,Boolean flag) {
        List<Bookings> bookings = bookingRepository.findByTimeSlotIdAndDate(timeSlot.getId(), date);
        List<Long> bookedHelperIds = bookings.stream()
                .map(booking -> Long.valueOf(booking.getHelperId()))
                .collect(Collectors.toList());

        List<Helper> availableHelpers = bookedHelperIds.isEmpty() ?
                helperRepository.findAll() :
                helperRepository.findByIdNotIn(bookedHelperIds);

        availableHelpers = availableHelpers.stream()
                .filter(helper -> userRepository.findById(helper.getUser().getId())
                        .map(user -> SuceessConstants.STATUS_APPROVED.equals(user.getApproved()))
                        .orElse(false))
                .collect(Collectors.toList());

        if (availableHelpers.isEmpty() && !flag) {
            throw new HelperAppException(SuceessConstants.NO_HELPERS_AVAILABLE);
        }

        return availableHelpers;
    }

}
