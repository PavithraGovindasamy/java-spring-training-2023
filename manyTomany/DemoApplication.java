package com.sirius.springenablement.demo;


import com.sirius.springenablement.demo.dao.AppDao;
import com.sirius.springenablement.demo.entity.Course;
import com.sirius.springenablement.demo.entity.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AppDao appDao){
		return runner->{
			createStudentAndCourses(appDao);
			//findCourseAndStudentByCourseId(appDao);
		};
	}

//	private void findCourseAndStudentByCourseId(AppDao appDao) {
//		Course obj=appDao.findCourseAndStudentByCourseId(10);
//		System.out.println("Loaded course" + obj);
//		System.out.println("Students"+obj.getStudent());
//		System.out.println("Done!");
//	}

	private void createStudentAndCourses(AppDao appDao) {
		Course  course=new Course("java course");
		Student student1=new Student("pavithra","g","2879@gmail.com");
		Student student2=new Student("pavi","g","2879@gmail.cm");
		course.addStudent(student1);
		course.addStudent(student2);
		// saving students and displaying them
		System.out.println("saving "+ course.getStudent());
		appDao.save(course);

	}


}

