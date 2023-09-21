package com.sirius.springenablement.ticket_booking.auth;

@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Builder
public class AuthenticationResponse {
    private String access_token;
    private String refresh_token;
    private String email;
    private String password;
}
