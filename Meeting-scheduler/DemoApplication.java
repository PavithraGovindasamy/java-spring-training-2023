package com.sirius.springenablement.demo;

import com.sirius.springenablement.demo.entity.Employee;
import com.sirius.springenablement.demo.entity.Teams;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


//	@Bean
//	public  CommandLineRunner commandLineRunner(AppDao appDao){
//		return  runner-> {
//			//	createTeamsAndEmployees(appDao);
//		};
//	}
//
//	private void createTeamsAndEmployees(AppDao appDao) {
//		// create team and multiple employess add the employees to the team
//
//		Teams teams=new Teams("DV",30);
//		Teams teams1=new Teams("Test",4);
////		Employee employee=new Employee("Pavithekavi","g","pavi@gmail.com");
////		Employee employee1=new Employee("sandy","g","sandy@gmail.com");
////		Employee employee2=new Employee("lakshmi","g","laks@gmail.com");
//
////		teams.addEmployee(employee);
////		teams.addEmployee(employee1);
////
////		teams1.addEmployee(employee2);
////
////		System.out.println("Saved teams and employees"+teams);
////		System.out.println("Employes"+teams.getEmployee());
////		System.out.println("\n\n");
////		System.out.println("Team 2");
////		System.out.println("Team2 "+teams1);
////		System.out.println("Employees"+teams1.getEmployee());
//
//		appDao.save(teams);
//		appDao.save(teams1);
//
//
//
//	}


}
