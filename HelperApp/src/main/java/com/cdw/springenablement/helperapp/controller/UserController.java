package com.cdw.springenablement.helperapp.controller;
import com.cdw.springenablement.helperapp.client.api.ResidentsApi;
import com.cdw.springenablement.helperapp.client.models.*;
import com.cdw.springenablement.helperapp.constants.SuceessConstants;
import com.cdw.springenablement.helperapp.services.interfaces.UserService;
import com.cdw.springenablement.helperapp.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.awt.print.Pageable;
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
     * @param bookingTechnicianDto Booking a slot with a technician (required)
     * @return response where the slot is booked or not
     */

    @Override
    public ResponseEntity<ApiResponseDto> bookTechnicianSlot(BookingTechnicianDto bookingTechnicianDto) {
        userService.bookTechnician(bookingTechnicianDto);
        return ResponseUtil.generateSuccessResponse(SuceessConstants.BOOKED_SUCCESSFULLY_MESSAGE);
    }

    /**
     *
     * @return the user bookings
     */
    @Override
    public ResponseEntity<List<BookingDto>> getUserBookings() {
            List<BookingDto> bookings=userService.getUserBookings();
            return  ResponseEntity.ok(bookings);

    }
}
