package com.sirius.springenablement.demo;


import com.sirius.springenablement.demo.dao.AppDao;
import com.sirius.springenablement.demo.entity.Instructor;
import com.sirius.springenablement.demo.entity.InstructorDetail;
import jakarta.transaction.Transactional;
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
		createInstructor(appDao);
		findById(appDao);
		deleteById(appDao);
		findInsDetailById(appDao);
	};
}

	private void findInsDetailById(AppDao appDao) {
		InstructorDetail rel=appDao.findInsDetailById(2);
		System.out.println("Hey instructor detail"+rel);
		System.out.println("Instrutor" +rel.getInstructor());
	}

	//dleeting
	private void deleteById(AppDao appDao) {
		appDao.delete(4);
		System.out.println("ey deleted");
	}

	// finding
	private void findById(AppDao appDao) {
      Instructor res=appDao.findById(5);
	  System.out.println("Hey found you"+res);
	  // also gets the assocaited InstructorDetail
		System.out.println("you too"+res.getInstructorDetail());
	}

	private void createInstructor(AppDao appDao) {
		Instructor obj=new Instructor("pav7","i9g","t9yui");
		InstructorDetail obj1=new InstructorDetail("http://ww99w.youtube.com","pl9aying");

		obj.setInstructorDetail(obj1);
		System.out.println("SAVING");
		appDao.save(obj);
		System.out.println("SAVED");
	}


}
