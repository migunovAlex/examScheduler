package com.examscheduler.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;

import com.examscheduler.controllers.GetPageController;

public class GetPageControllerTest {
	
	private GetPageController controller;
	
	@Mock
	private ModelMap modelMap;

	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		controller = new GetPageController();
	}
	
	@Test
	public void shouldGetLoginPage(){
		assertEquals(controller.getLoginPage(), "loginPage");
	}
	
	@Test
	public void shouldGetMainPageForNonAuthorised(){
		assertEquals(controller.getUnauthorizedMainPage(), "mainPage");
	}
	
	@Test
	public void shouldGetMainPageForAuthorised(){
		assertEquals(controller.getAuthorizedMainPage(modelMap), "mainPage");
		verify(modelMap, times(1)).addAttribute(eq("userSession"), any(String.class));
	}

}
