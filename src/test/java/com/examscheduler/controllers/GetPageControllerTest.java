package com.examscheduler.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;

public class GetPageControllerTest {
	
	private static final String FAKE_SESSION = "FAKE_SESSION";

	private GetPageController controller;
	
	@Mock
	private ModelMap modelMap;
	@Mock
	private HttpServletRequest request;

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
		when(request.getCookies()).thenReturn(createCookies());
		assertEquals(controller.getAuthorizedMainPage(request, modelMap), "mainPage");
		verify(modelMap, times(1)).addAttribute("userSession", FAKE_SESSION);
	}
	
	private Cookie[] createCookies() {
		Cookie[] result = new Cookie[1];
		result[0] = new Cookie(GetPageController.SESSION_VALUE, FAKE_SESSION);
		return result;
	}

	@Test
	public void shouldGetLessonTimePage(){
		assertEquals(controller.getLessonTimePage(), "lessonTime");
	}

	@Test
	public void shouldGetTeacherPage(){
		assertEquals(controller.getTeacherPage(), "teacherPage");
	}
	
	@Test
	public void shouldGetLessonsPage(){
		assertEquals(controller.getLessonsPage(), "lessonPage");
	}
}
