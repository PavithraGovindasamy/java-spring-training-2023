package com.cdw.springenablement.helper_App;

import com.cdw.springenablement.helper_App.controller.UserController;
import com.cdw.springenablement.helper_App.services.interfaces.UserService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
class DemoApplicationTests {

     @InjectMocks
     private UserService userService;

	@Mock
	private UserController userController;

	@Before
	private void setup(){
		MockitoAnnotations.initMocks(this);
	}








}
