package com.cdw.springenablement.helperapp.controller;
import com.cdw.springenablement.helperapp.client.api.AuthApi;
import com.cdw.springenablement.helperapp.client.models.*;
import com.cdw.springenablement.helperapp.constants.ErrorConstants;
import com.cdw.springenablement.helperapp.constants.SuceessConstants;
import com.cdw.springenablement.helperapp.exception.HelperAppException;
import com.cdw.springenablement.helperapp.services.AuthenticationService;
import com.cdw.springenablement.helperapp.services.TokenBlacklistService;
import com.cdw.springenablement.helperapp.services.interfaces.UserService;
import com.cdw.springenablement.helperapp.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class that handles authentication-related endpoints.
 * @Author pavithra
 */
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
            throw new HelperAppException(ErrorConstants.SPECIALIZATION_REQUIRED_ERROR);
        }
        Long id = userService.registerUser(userDto);
        if (userDto.getRole().contains(SuceessConstants.ROLE_HELPER) && userDto.getSpecialisation() != null) {
            userService.updateHelperSpecialization(id, userDto.getSpecialisation());
        }

        return ResponseUtil.generateCreateResponse(SuceessConstants.USER_REGISTERED_SUCCESSFULLY_MESSAGE, id);
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
        if (tokenBlacklistService.isTokenBlacklisted(token)) {
            throw new HelperAppException(SuceessConstants.ALREADY_BLACKLISTED);
        }
        tokenBlacklistService.addToBlacklist(token);
        return ResponseUtil.generateSuccessResponse(SuceessConstants.LOGGEDOUT_SUCCESSFULLY_MESSAGE);
    }

}
