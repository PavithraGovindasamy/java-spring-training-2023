package com.cdw.springenablement.helperapp;

import com.cdw.springenablement.helperapp.controller.UserController;
import com.cdw.springenablement.helperapp.services.interfaces.UserService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
