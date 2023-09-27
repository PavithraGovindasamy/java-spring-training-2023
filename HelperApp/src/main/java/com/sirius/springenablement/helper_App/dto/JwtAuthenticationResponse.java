package com.sirius.springenablement.helper_App.dto;
public class JwtAuthenticationResponse {
    private String accessToken;


    // Default constructor
    public JwtAuthenticationResponse() {}


    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
