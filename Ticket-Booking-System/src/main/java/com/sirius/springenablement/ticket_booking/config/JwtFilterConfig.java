package com.sirius.springenablement.ticket_booking.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.sirius.springenablement.ticket_booking.security.JwtRequestFilter;
@Configuration
public class JwtFilterConfig {

    @Bean
    public JwtRequestFilter jwtRequestFilter() {
        return new JwtRequestFilter();
    }
}
