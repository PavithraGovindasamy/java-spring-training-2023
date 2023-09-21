package com.sirius.springenablement.ticket_booking;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.sirius.springenablement.ticket_booking.services.UserService;
import org.springframework.context.annotation.Bean;
@SpringBootApplication
@EnableWebSecurity
@EnableJpaRepositories
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);}

		@Bean
		org.springframework.boot.CommandLineRunner run (UserService userService){
			return args-> {
         userService.addToUser("admin@gmail.com","ROLE_ADMIN");

			};


	}





}
