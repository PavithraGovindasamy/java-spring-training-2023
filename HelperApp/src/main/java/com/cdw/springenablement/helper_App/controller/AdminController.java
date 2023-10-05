package com.cdw.springenablement.helper_App.controller;

import com.cdw.springenablement.helper_App.client.api.AdminApi;
import com.cdw.springenablement.helper_App.client.models.ApiResponseDto;
import com.cdw.springenablement.helper_App.client.models.ApprovalDto;
import com.cdw.springenablement.helper_App.client.models.HelperDto;
import com.cdw.springenablement.helper_App.client.models.UserDto;
import com.cdw.springenablement.helper_App.entity.Users;
import com.cdw.springenablement.helper_App.services.interfaces.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController implements AdminApi {
    @Autowired
    private AdminService adminService;

    @Override
    public ResponseEntity<List<ApprovalDto>> getApprovalRequest() {
        List<ApprovalDto> adminDto = adminService.getApprovalRequest();
        return ResponseEntity.ok(adminDto);
    }

    @Override
    public ResponseEntity<ApiResponseDto> approveRequest() {
        try {
            adminService.approveRequest();
            ApiResponseDto response = new ApiResponseDto()
                    .message("approved successfully")
                    .statusCode((long) HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            ApiResponseDto response = new ApiResponseDto()
                    .message("operation failed: " + e.getMessage())
                    .statusCode((long) HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.ok(response);
        }

    }

    @Override
    public ResponseEntity<ApiResponseDto> addNewMember(UserDto userDto)  {
      try{
            adminService.addNewMember(userDto);
            ApiResponseDto response = new ApiResponseDto()
                    .message("Member added successfully")
                    .statusCode((long) HttpStatus.OK.value());
            return ResponseEntity.ok(response);}
       catch (Exception e) {
       ApiResponseDto response = new ApiResponseDto()
               .message("operation failed: " + e.getMessage())
               .statusCode((long) HttpStatus.BAD_REQUEST.value());
       return ResponseEntity.ok(response);
   }
    }

    @Override
    public ResponseEntity<ApiResponseDto> removeResident(Integer residentId)  {
        try {


            System.out.println("hjk");
            adminService.removeResident(residentId);
            ApiResponseDto response = new ApiResponseDto()
                    .message("Resident removed successfully")
                    .statusCode((long) HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        }catch (Exception e) {
            ApiResponseDto response = new ApiResponseDto()
                    .message("operation failed: " + e.getMessage())
                    .statusCode((long) HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.ok(response);
        }

    }

    @Override
    public ResponseEntity<ApiResponseDto> removeHelper(Integer helperId)  {
      try {
          adminService.removeHelper(helperId);
          ApiResponseDto response = new ApiResponseDto()
                  .message("Helper removed successfully")
                  .statusCode((long) HttpStatus.OK.value());
          return ResponseEntity.ok(response);
      }
       catch (Exception e) {
            ApiResponseDto response = new ApiResponseDto()
                    .message("operation failed: " + e.getMessage())
                    .statusCode((long) HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.ok(response);
        }
    }

    @Override
    public ResponseEntity<ApiResponseDto> updateHelper(HelperDto helperDto) {
        try{
            adminService.updateMember(helperDto);
            ApiResponseDto response = new ApiResponseDto()
                    .message("Updated Successfully")
                    .statusCode((long) HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        }catch (Exception e) {
            ApiResponseDto response = new ApiResponseDto()
                    .message("operation failed: " + e.getMessage())
                    .statusCode((long) HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.ok(response);
        }
    }
}
