package com.cdw.springenablement.helperapp.controller;

import com.cdw.springenablement.helperapp.client.api.AdminApi;
import com.cdw.springenablement.helperapp.client.models.*;
import com.cdw.springenablement.helperapp.constants.SuceessConstants;
import com.cdw.springenablement.helperapp.services.interfaces.AdminService;
import com.cdw.springenablement.helperapp.utils.ResponseUtil;
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
        return ResponseUtil.generateSuccessResponse(SuceessConstants.APPROVED_SUCCESSFULLY_MESSAGE);
    }

    /**
     * Approves an approval request.
     *
     * @return  the result of the rejection request
     */


    @Override
    public ResponseEntity<ApiResponseDto> rejectRequest(RejectRequestRequest rejectRequestRequest) {
        adminService.rejectRequest(rejectRequestRequest);
        return ResponseUtil.generateSuccessResponse(SuceessConstants.REJECTED_SUCCESSFULLY_MESSAGE);
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
        return ResponseUtil.generateSuccessResponse(SuceessConstants.RESIDENT_REMOVED_SUCCESSFULLY_MESSAGE);
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
        return ResponseUtil.generateSuccessResponse(SuceessConstants.HELPER_REMOVED_SUCCESSFULLY_MESSAGE);
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
        return ResponseUtil.generateSuccessResponse(SuceessConstants.UPDATED_SUCCESSFULLY_MESSAGE);
    }
}
