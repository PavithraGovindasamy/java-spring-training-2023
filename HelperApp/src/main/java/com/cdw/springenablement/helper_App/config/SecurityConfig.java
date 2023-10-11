package com.cdw.springenablement.helper_App.config;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import com.cdw.springenablement.helper_App.security.JwtAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig  {
    @Autowired
    private  JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private  AuthenticationProvider authenticationProvider;



    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(configure ->
                configure
                    .requestMatchers("/auth/register","/auth/login","/auth/logout").permitAll()
                     .requestMatchers("/admin/**").hasAnyAuthority("Role_Admin")
                     .requestMatchers("/residents/**").hasAnyAuthority("Role_Resident")
                      .requestMatchers("/helpers/**").hasAnyAuthority("Role_Helper")
                      .anyRequest().authenticated())
                        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .csrf(csrf -> csrf.disable())
                        .cors(Customizer.withDefaults())
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }









}