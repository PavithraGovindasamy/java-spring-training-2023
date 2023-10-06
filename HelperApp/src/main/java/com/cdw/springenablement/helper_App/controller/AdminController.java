package com.cdw.springenablement.helper_App.controller;

import com.cdw.springenablement.helper_App.client.api.AdminApi;
import com.cdw.springenablement.helper_App.client.models.ApiResponseDto;
import com.cdw.springenablement.helper_App.client.models.ApprovalDto;
import com.cdw.springenablement.helper_App.client.models.HelperDto;
import com.cdw.springenablement.helper_App.client.models.UserDto;
import com.cdw.springenablement.helper_App.services.interfaces.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
     * @return  the result of the approval operation.
     */
    @Override
    public ResponseEntity<ApiResponseDto> approveRequest() {
        adminService.approveRequest();
        ApiResponseDto response = new ApiResponseDto()
                .message("Approved successfully")
                .statusCode((long) HttpStatus.OK.value());
        return ResponseEntity.ok(response);
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
        ApiResponseDto response = new ApiResponseDto()
                .message("Member added successfully")
                .statusCode((long) HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    /**
     * Removes a resident from the system.
     *
     * @param residentId
     * @return  the result of the removal operation.
     */
    @Override
    public ResponseEntity<ApiResponseDto> removeResident(Integer residentId) {
        adminService.removeResident(residentId);
        ApiResponseDto response = new ApiResponseDto()
                .message("Resident removed successfully")
                .statusCode((long) HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    /**
     * Removes a helper from the system.
     *
     * @param helperId
     * @return  the result of the removal operation.
     */
    @Override
    public ResponseEntity<ApiResponseDto> removeHelper(Integer helperId) {
        adminService.removeHelper(helperId);
        ApiResponseDto response = new ApiResponseDto()
                .message("Helper removed successfully")
                .statusCode((long) HttpStatus.OK.value());
        return ResponseEntity.ok(response);
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
        ApiResponseDto response = new ApiResponseDto()
                .message("Updated Successfully")
                .statusCode((long) HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }
}
