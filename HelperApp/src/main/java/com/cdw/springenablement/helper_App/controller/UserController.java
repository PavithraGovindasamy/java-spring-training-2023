package com.cdw.springenablement.helper_App.controller;
import com.cdw.springenablement.helper_App.client.api.ResidentsApi;
import com.cdw.springenablement.helper_App.client.models.*;
import com.cdw.springenablement.helper_App.constants.SuceessConstants;
import com.cdw.springenablement.helper_App.services.interfaces.UserService;
import com.cdw.springenablement.helper_App.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * controller that implements usersApi
 * @Author pavithra
 */
@RestController
public class UserController implements ResidentsApi {

    @Autowired
    public UserService userService;

    /**
     *
     * Method which give the available technicians timeslot
     * @return response where list of timeslot is returned
     */
    @Override
    public ResponseEntity<List<TimeSlotDto>> getAvailableTechnicians() {
        List<TimeSlotDto> technicianDtos = userService.getAvailableTechnicians();
        return ResponseEntity.ok(technicianDtos);
    }

    /**
     *
     * @param bookingTechnicianDto Booking a slot with a technician (required)
     * @return response where the slot is booked or not
     */

    @Override
    public ResponseEntity<ApiResponseDto> bookTechnicianSlot(BookingTechnicianDto bookingTechnicianDto) {
        userService.bookTechnician(bookingTechnicianDto);
        String successMessage = SuceessConstants.BOOKED_SUCCESSFULLY_MESSAGE;
        return ResponseUtil.generateSuccessResponse(successMessage);

    }


}
