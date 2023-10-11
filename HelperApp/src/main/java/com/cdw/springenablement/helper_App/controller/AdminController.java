package com.cdw.springenablement.helper_App.controller;

import com.cdw.springenablement.helper_App.client.api.AdminApi;
import com.cdw.springenablement.helper_App.client.models.*;
import com.cdw.springenablement.helper_App.constants.SuceessConstants;
import com.cdw.springenablement.helper_App.services.interfaces.AdminService;
import com.cdw.springenablement.helper_App.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class that handles admin-related endpoints.
 * @Author pavithra
 */
@RestController
public class AdminController implements AdminApi {

    @Autowired
    private AdminService adminService;

    /**
     * Retrieves a list of approval requests.
     *
     * @return  the list of approval requests.
     */
    @Override
    public ResponseEntity<List<ApprovalDto>> getApprovalRequest() {
        List<ApprovalDto> adminDto = adminService.getApprovalRequest();
        return ResponseEntity.ok(adminDto);
    }

    /**
     * Approves an approval request.
     *
     * @return the result of the approval operation.
     */
    @Override
    public ResponseEntity<ApiResponseDto> approveRequest(ApproveRequestRequest approveRequestRequest) {
        adminService.approveRequest(approveRequestRequest);
        String successMessage = SuceessConstants.APPROVED_SUCCESSFULLY_MESSAGE;
        return ResponseUtil.generateSuccessResponse(successMessage);
    }

    /**
     * Approves an approval request.
     *
     * @return  the result of the rejection request
     */


    @Override
    public ResponseEntity<ApiResponseDto> rejectRequest(RejectRequestRequest rejectRequestRequest) {
        System.out.println("njsn");
        adminService.rejectRequest(rejectRequestRequest);
        String successMessage = SuceessConstants.REJECTED_SUCCESSFULLY_MESSAGE;
        System.out.println("njsn"+"gh"+ successMessage);

        return ResponseUtil.generateSuccessResponse(successMessage);
    }



    /**
     * Adds a new member to the system.
     *
     * @param userDto
     * @return  the result of the addition operation.
     */
    @Override
    public ResponseEntity<ApiResponseDto> addNewMember(UserDto userDto) {
        adminService.addNewMember(userDto);
        String successMessage = SuceessConstants.MEMBER_ADDED_SUCCESSFULLY_MESSAGE;
        return ResponseUtil.generateSuccessResponse(successMessage);
    }

    /**
     * Removes a resident from the system.
     *
     * @param residentId
     * @return  the result of the removal operation.
     */
    @Override
    public ResponseEntity<ApiResponseDto> removeResident(Long residentId) {
        adminService.removeResident(residentId);
        String successMessage = SuceessConstants.RESIDENT_REMOVED_SUCCESSFULLY_MESSAGE;
        return ResponseUtil.generateSuccessResponse(successMessage);
    }

    /**
     * Removes a helper from the system.
     *
     * @param helperId
     * @return  the result of the removal operation.
     */
    @Override
    public ResponseEntity<ApiResponseDto> removeHelper(Long helperId) {
        adminService.removeHelper(helperId);
        String successMessage = SuceessConstants.HELPER_REMOVED_SUCCESSFULLY_MESSAGE;
        return ResponseUtil.generateSuccessResponse(successMessage);
    }

    /**
     * Updates information about a helper in the system.
     *
     * @param helperDto
     * @return  the result of the update operation.
     */
    @Override
    public ResponseEntity<ApiResponseDto> updateHelper(HelperDto helperDto) {
        adminService.updateMember(helperDto);
        String successMessage = SuceessConstants.UPDATED_SUCCESSFULLY_MESSAGE;
        return ResponseUtil.generateSuccessResponse(successMessage);
    }
}
