package com.cdw.springenablement.helper_App.Controller;

import com.cdw.springenablement.helper_App.client.models.ApiResponseDto;
import com.cdw.springenablement.helper_App.client.models.ApprovalDto;
import com.cdw.springenablement.helper_App.client.models.HelperDto;
import com.cdw.springenablement.helper_App.client.models.UserDto;
import com.cdw.springenablement.helper_App.controller.AdminController;
import com.cdw.springenablement.helper_App.services.interfaces.AdminService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private AdminService adminService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private MockMvc mockMvc;

    @Test
    public void testGetApprovalRequest(){
      List<ApprovalDto> approvalDtos=new ArrayList<>();
      when(adminService.getApprovalRequest()).thenReturn(approvalDtos);
        ResponseEntity<List<ApprovalDto>> approval=adminController.getApprovalRequest();
        assertEquals(approvalDtos,approval.getBody());
    }


    @Test
    public void testUpdateHelper(){
        HelperDto helperDto=new HelperDto();
        helperDto.setEmail("test@gmail.com");
        helperDto.setGender("Gender");
        helperDto.setDateOfBirth(LocalDate.parse("2023-09-09"));
        helperDto.setFirstName("test");
        helperDto.setLastName("g");
        doNothing().when(adminService).updateMember(helperDto);
        ResponseEntity<ApiResponseDto> responseEntity = adminController.updateHelper(helperDto);
        ApiResponseDto expectedResponse = new ApiResponseDto()
                .message("Updated Successfully");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse.getMessage(), responseEntity.getBody().getMessage());
    }

    @Test
    public void testGetApprovalRequest_EmptyList(){
        List<ApprovalDto> approvalDtos = new ArrayList<>();
        when(adminService.getApprovalRequest()).thenReturn(approvalDtos);
        ResponseEntity<List<ApprovalDto>> approval = adminController.getApprovalRequest();
        assertEquals(HttpStatus.OK, approval.getStatusCode());
        assertEquals(approvalDtos, approval.getBody());
    }


    @Test
    public void testRemoveHelper() throws Exception {
        int helperId=1;
        doNothing().when(adminService).removeHelper(helperId);
        ResponseEntity<ApiResponseDto> responseEntity = adminController.removeHelper(helperId);
        ApiResponseDto expectedResponse = new ApiResponseDto()
                .message("Helper removed successfully");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse.getMessage(), responseEntity.getBody().getMessage());
    }

    @Test
    public void testRemoveResident() throws Exception {
        int residentId=1;
        doNothing().when(adminService).removeResident(residentId);
        ResponseEntity<ApiResponseDto> responseEntity = adminController.removeResident(residentId);
        ApiResponseDto expectedResponse = new ApiResponseDto()
                .message("Resident removed successfully");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse.getMessage(), responseEntity.getBody().getMessage());
    }

    @Test
    public void testApproveRequest(){
        doNothing().when(adminService).approveRequest();
        ResponseEntity<ApiResponseDto> responseEntity = adminController.approveRequest();
        ApiResponseDto expectedResponse = new ApiResponseDto()
                .message("approved successfully");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse.getMessage(), responseEntity.getBody().getMessage());


    }

    @Test
    public void testAddNewMember() throws Exception {
        UserDto dto=new UserDto();
        dto.setEmail("test@gmail.com");

        doNothing().when(adminService).addNewMember(dto);
        ResponseEntity<ApiResponseDto> responseEntity = adminController.addNewMember(dto);
        ApiResponseDto expectedResponse = new ApiResponseDto()
                .message("Member added successfully");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse.getMessage(), responseEntity.getBody().getMessage());

    }





}
