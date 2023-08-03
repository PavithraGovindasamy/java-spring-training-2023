package com.sirius.springenablement.demo;

import com.sirius.springenablement.demo.dao.StudentDAO;
import com.sirius.springenablement.demo.entity.Student;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
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



	@Bean
	public CommandLineRunner commandLineRunner(StudentDAO studentDAO){
		return Runner ->{
		createStudents(studentDAO);
//		findStudent(studentDAO);
//			findAllStudent(studentDAO);
//			updateStudent(studentDAO);
//			deleteStudent(studentDAO);
			//	deleteAll(studentDAO);
		};
	}
public  void deleteAll(StudentDAO studentDAO){
		studentDAO.deleteAll();

}
	private void deleteStudent(StudentDAO studentDAO) {
		studentDAO.delete(1);
	}

	private void updateStudent(StudentDAO studentDAO) {
		Student student=studentDAO.findStudent(1);
		student.setEmail("pavitrag@gmail.com");
		student.setLastName("pavi");
		studentDAO.update((student));
		System.out.println("Updated");
		System.out.println(student);

	}

	private void findAllStudent(StudentDAO studentDAO) {
		List<Student> student=studentDAO.findAll();

//		for(Student tempStudent: student){
//			System.out.println(tempStudent);
//		}
	}

	private void findStudent(StudentDAO studentDAO) {

		// find

		Student student=studentDAO.findStudent(1);
		System.out.println("hey student"+student);
	}

	private void createStudents(StudentDAO studentDAO) {
		System.out.println("hey obj created");
		Student student1 = new Student("pavi","g","gyhj");
		Student student2 = new Student("sandy","okay","gij3yhj");
		Student student3 = new Student("laks","ehhn","gy39i3hj");
		Student student4 = new Student("raj","nz","gyh39ij");

		studentDAO.save(student1);
		System.out.println("saved"+student1.getId());
		studentDAO.save(student2);
		System.out.println("saved"+student2.getId());

		studentDAO.save(student3);
		System.out.println("saved"+student3.getId());

		studentDAO.save(student4);
		System.out.println("saved"+student4.getId());



	}

}
