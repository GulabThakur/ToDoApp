package com.bridgeit.ToDoApp.jUnitTesting;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bridgeit.ToDoApp.config.WebConfig;
import com.bridgeit.ToDoApp.controller.UserController;
import com.bridgeit.ToDoApp.service.IuserService;


@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class})
public class UserControllerJunitTest {
	private MockMvc mockMvc;
	@Autowired
	private IuserService userModelService;
	@InjectMocks
	private UserController userController;
	 public void init(){
	        MockitoAnnotations.initMocks(this);
	        mockMvc = MockMvcBuilders
	                .standaloneSetup(userController)
	                .build();
	    }

}
