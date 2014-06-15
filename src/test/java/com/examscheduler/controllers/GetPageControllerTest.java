package com.examscheduler.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;

import com.examscheduler.security.service.UserDetailService;

public class GetPageControllerTest {
	
	private static final String FAKE_SESSION = "FAKE_SESSION";

	private GetPageController controller;
	
	@Mock
	private ModelMap modelMap;
	@Mock
	private HttpServletRequest request;
	@Mock
	private UserDetailService userDetailService;
	@Mock
	private CookieHelper cookieHelper;

	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		controller = new GetPageController();
		controller.setUserDetailService(userDetailService);
		controller.setCookieHelper(cookieHelper);
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
		when(cookieHelper.getSessionCookie(request)).thenReturn(FAKE_SESSION);
		assertEquals(controller.getAuthorizedMainPage(request, modelMap), "mainPage");
		verify(modelMap, times(1)).addAttribute("userSession", FAKE_SESSION);
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
