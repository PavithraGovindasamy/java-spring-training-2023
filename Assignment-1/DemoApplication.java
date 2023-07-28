package com.example.demo;

import com.example.demo.rest.RestController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@ComponentScan(basePackages = "com.example.demo")
@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		// Load the  application  from the "Bean.xml"
		ApplicationContext context = new ClassPathXmlApplicationContext("Bean.xml");
		RestController rest = context.getBean("restController", RestController.class);
		System.out.println(rest);
		// Call the hello() method of the RestController bean
		rest.hello();
	}
}
