package com.cdw.springenablement.helper_App.Controller;

import com.cdw.springenablement.helper_App.client.models.*;
import com.cdw.springenablement.helper_App.constants.SuceessConstants;
import com.cdw.springenablement.helper_App.controller.AuthController;
import com.cdw.springenablement.helper_App.services.AuthenticationService;
import com.cdw.springenablement.helper_App.services.TokenBlacklistService;
import com.cdw.springenablement.helper_App.services.interfaces.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthControllerTest {
    @InjectMocks
    private AuthController authController;

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private TokenBlacklistService tokenBlacklistService;

    @Test
    public void testRegisterUser() {
        UserDto userDto = new UserDto();
        userDto.setRole(Collections.singletonList("Role_Helper"));
        userDto.setSpecialisation("plumber");
        Long id=1L;
        when(userService.registerUser(userDto)).thenReturn(id);
        ResponseEntity<ApiResponseDto> response = authController.registerUser(userDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ApiResponseDto responseBody = response.getBody();
        assertEquals(SuceessConstants.USER_REGISTERED_SUCCESSFULLY_MESSAGE +" User ID: "+id, responseBody.getMessage());
    }

    @Test
    public void testRegisterUser_WithoutSpecialisation() {
        UserDto userDto = new UserDto();
        userDto.setRole(Collections.singletonList("Role_Helper"));
        Long id=1L;
        ResponseEntity<ApiResponseDto> response = authController.registerUser(userDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ApiResponseDto responseBody = response.getBody();
        assertEquals("Specialisation is required for helpers.", responseBody.getMessage());
    }


    @Test
    public void testLoginUser() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail("user@example.com");
        AuthenticationResponse mockResponse = new AuthenticationResponse();
        mockResponse.setAccessToken("token");
        when(authenticationService.authenticate(authenticationRequest)).thenReturn(mockResponse);
        ResponseEntity<AuthenticationResponse> response = authController.loginUser(authenticationRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        AuthenticationResponse responseBody = response.getBody();
        assertEquals("token", responseBody.getAccessToken());
    }

    @Test
    public void testLogoutUser() {
        LogoutUserRequest logoutUserRequest = new LogoutUserRequest();
        logoutUserRequest.setToken("token");
        String token = logoutUserRequest.getToken();
        doNothing().when(tokenBlacklistService).addToBlacklist("token");

        ResponseEntity<ApiResponseDto> response = authController.logoutUser(logoutUserRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        ApiResponseDto responseBody = response.getBody();
        assertEquals(SuceessConstants.LOGGEDOUT_SUCCESSFULLY_MESSAGE, responseBody.getMessage());
    }
}

