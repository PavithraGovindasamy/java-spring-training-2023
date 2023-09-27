package com.sirius.springenablement.helper_App.auth;
@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Builder
public class AuthenticationRequest {
    private String email;
    private String password;
}
