package com.examsscheduler.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.examsscheduler.controllers.GetPageController;

public class GetPageControllerTest {
	
	@Mock
	private GetPageController controller;

	@Before
	public void setup(){
		controller = new GetPageController();
	}
	
	@Test
	public void shouldGetPage(){
		assertEquals(controller.getLoginPage(), "loginPage");
	}
	
	
	
	

}
