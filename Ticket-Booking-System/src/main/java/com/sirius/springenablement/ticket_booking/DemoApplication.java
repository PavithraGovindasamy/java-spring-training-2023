package com.sirius.springenablement.ticket_booking;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//DAO-Data  Access obj-helper function for interacti g with db
//DAO needs entity manager for saving/retrieving entitires
// this entity manager needs the datasource info-- given in application properties
// first we need a interface for studentDAO and then we need to implement that interface and store the obj of student in it
//INject the entity manager to studentDAOImplementation
//@Repository-The @Repository annotation is used to indicate that a class is a Spring Data repository,
// which is responsible for data access operations such as querying and persisting data to a database
//@Transactional-@Transactional, you ensure that if any exception occurs during the method's execution,
// the changes made to the database within that method will be rolled back, preserving the data consistency.
// how the commandliner is called--> called after rtge spring booot initalisses the application contetxt the run method will be called
/// when we put @Component then sprung will automatically create obj for it during scannng the application context


@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}





}
