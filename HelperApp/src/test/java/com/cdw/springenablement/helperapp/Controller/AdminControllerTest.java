package com.cdw.springenablement.helperapp.Controller;

import com.cdw.springenablement.helperapp.client.models.*;
import com.cdw.springenablement.helperapp.constants.SuceessConstants;
import com.cdw.springenablement.helperapp.controller.AdminController;
import com.cdw.springenablement.helperapp.services.interfaces.AdminService;
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
                .message(SuceessConstants.UPDATED_SUCCESSFULLY_MESSAGE);

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
        Long helperId=1L;
        doNothing().when(adminService).removeHelper(helperId);
        ResponseEntity<ApiResponseDto> responseEntity = adminController.removeHelper(helperId);
        ApiResponseDto expectedResponse = new ApiResponseDto()
                .message(SuceessConstants.HELPER_REMOVED_SUCCESSFULLY_MESSAGE);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertEquals(expectedResponse.getMessage(), responseEntity.getBody().getMessage());
    }

    @Test
    public void testRemoveResident() throws Exception {
        Long residentId=1L;
        doNothing().when(adminService).removeResident(residentId);
        ResponseEntity<ApiResponseDto> responseEntity = adminController.removeResident(residentId);
        ApiResponseDto expectedResponse = new ApiResponseDto()
                .message(SuceessConstants.RESIDENT_REMOVED_SUCCESSFULLY_MESSAGE);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertEquals(expectedResponse.getMessage(), responseEntity.getBody().getMessage());
    }

    @Test
    public void testApproveRequest(){
        ApproveRequestRequest approveRequestRequest=new ApproveRequestRequest();
        Long id=1L;
        List<Long> approvedIds=new ArrayList<>();
        approvedIds.add(id);
        when(adminService.approveRequest(approveRequestRequest)).thenReturn(approvedIds);
        ResponseEntity<ApiResponseDto> responseEntity = adminController.approveRequest(approveRequestRequest);
        ApiResponseDto expectedResponse = new ApiResponseDto()
                .message(SuceessConstants.APPROVED_SUCCESSFULLY_MESSAGE);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse.getMessage()+" User IDs: "+approvedIds, responseEntity.getBody().getMessage());
    }

    @Test
    public void testRejectRequest(){
        RejectRequestRequest rejectRequestRequest=new RejectRequestRequest();
        Long id=1L;
        List<Long> rejectedIds=new ArrayList<>();
        rejectedIds.add(id);
        when(adminService.rejectRequest(rejectRequestRequest)).thenReturn(rejectedIds);
        ResponseEntity<ApiResponseDto> responseEntity = adminController.rejectRequest(rejectRequestRequest);
        ApiResponseDto expectedResponse = new ApiResponseDto()
                .message(SuceessConstants.REJECTED_SUCCESSFULLY_MESSAGE);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse.getMessage()+" User IDs: "+rejectedIds, responseEntity.getBody().getMessage());
    }







}
