package com.sirius.springenablement.ticket_booking.auth;
@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Builder
public class AuthenticationRequest {
    private String email;
    private String password;
}
