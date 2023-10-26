package com.cdw.springenablement.helperapp.config;

import com.cdw.springenablement.helperapp.constants.Roles;
import com.cdw.springenablement.helperapp.constants.SuceessConstants;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import com.cdw.springenablement.helperapp.security.JwtAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * class which handles all the request and authenticate the users  based on their roles
 */
@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    /**
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(configure ->
                        configure
                                .requestMatchers("/auth/register", "/auth/login", "/auth/logout","/pagination").permitAll()
                                .requestMatchers("/admin/**").hasAnyAuthority(Roles.Role_Admin.name())
                                .requestMatchers("/residents/**").hasAnyAuthority(Roles.Role_Resident.name())
                                .requestMatchers("/helpers/appointments").hasAnyAuthority(Roles.Role_Helper.name())
                                .requestMatchers("/helpers/available-timeslots","/helpers/available-helpers").permitAll()
                                .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}