package com.cdw.springenablement.helper_App.controller;

import com.cdw.springenablement.helper_App.client.api.AuthApi;
import com.cdw.springenablement.helper_App.client.models.*;
import com.cdw.springenablement.helper_App.constants.ErrorConstants;
import com.cdw.springenablement.helper_App.constants.SuceessConstants;
import com.cdw.springenablement.helper_App.services.AuthenticationService;
import com.cdw.springenablement.helper_App.services.TokenBlacklistService;
import com.cdw.springenablement.helper_App.services.interfaces.UserService;
import com.cdw.springenablement.helper_App.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthApi {

    @Autowired
    public UserService userService;

    @Autowired
    public AuthenticationService authenticationService;

    @Autowired
    public TokenBlacklistService tokenBlacklistService ;



    /**
     *
     * @param userDto Registering a user (required)
     * @return Response where the user is registered
     */
    @Override
    public ResponseEntity<ApiResponseDto> registerUser(UserDto userDto) {
        if (userDto.getRole().contains(SuceessConstants.ROLE_HELPER) && userDto.getSpecialisation() == null) {
            String errorMessage = ErrorConstants.SPECIALIZATION_REQUIRED_ERROR;
            return ResponseUtil.generateErrorResponse(errorMessage, HttpStatus.BAD_REQUEST);
        }

        Long id = userService.registerUser(userDto);
        String successMessage = SuceessConstants.USER_REGISTERED_SUCCESSFULLY_MESSAGE;

        if (userDto.getRole().contains(SuceessConstants.ROLE_HELPER) && userDto.getSpecialisation() != null) {
            userService.updateHelperSpecialization(id, userDto.getSpecialisation());
        }

        return ResponseUtil.generateSuccessResponse(successMessage, id);
    }

    /**
     *
     * @param authenticationRequest Authenticate a user with email and password (required)
     * @return  response where response return the token
     */

    @Override
    public ResponseEntity<AuthenticationResponse> loginUser(AuthenticationRequest authenticationRequest) {
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequest);
        return ResponseEntity.ok(authenticationResponse);
    }

    /**
     *
     * @param logoutUserRequest Logout a user by invalidating the token (required)
     * @return response where the slot is logged out or not
     */

    @Override
    public ResponseEntity<ApiResponseDto> logoutUser(LogoutUserRequest logoutUserRequest) {
        String token = logoutUserRequest.getToken();
        tokenBlacklistService.addToBlacklist(token);
        String successMessage = SuceessConstants.LOGGEDOUT_SUCCESSFULLY_MESSAGE;
        return ResponseUtil.generateSuccessResponse(successMessage);
    }

}
