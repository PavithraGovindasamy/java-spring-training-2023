package com.sirius.springenablement.ticket_booking.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtFilterConfig {

    @Bean
    public com.sirius.springenablement.ticket_booking.security.JwtRequestFilter jwtRequestFilter() {
        return new com.sirius.springenablement.ticket_booking.security.JwtRequestFilter();
    }
}
