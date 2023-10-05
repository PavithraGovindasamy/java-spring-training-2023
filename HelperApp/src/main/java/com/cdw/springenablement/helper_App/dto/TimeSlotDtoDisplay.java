package com.cdw.springenablement.helper_App.dto;

import com.cdw.springenablement.helper_App.client.models.HelperDisplayDto;
import com.cdw.springenablement.helper_App.client.models.TimeSlotDto;
import com.cdw.springenablement.helper_App.entity.Helper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TimeSlotDtoDisplay {

    public static TimeSlotDto createTimeSlotDto(Long id, LocalTime startTime, LocalTime endTime, LocalDate date, List<Helper> helpers) {
        TimeSlotDto timeSlotDto = new TimeSlotDto();
        timeSlotDto.setId(Math.toIntExact(id));
        timeSlotDto.setStartTime(String.valueOf(startTime));
        timeSlotDto.setEndTime(String.valueOf(endTime));
        timeSlotDto.setDate(date);

        if (helpers != null) {
            List<HelperDisplayDto> helperDisplayDtos = new ArrayList<>();
            for (Helper helper : helpers) {
                HelperDisplayDto helperDisplayDto = new HelperDisplayDto();
                helperDisplayDto.setId(helper.getId());
                helperDisplayDto.setSpecialization(helper.getSpecialization());
                helperDisplayDtos.add(helperDisplayDto);
            }
            timeSlotDto.setHelpers(helperDisplayDtos);
        } else {
            timeSlotDto.setHelpers(new ArrayList<>());
        }

        return timeSlotDto;
    }
}
