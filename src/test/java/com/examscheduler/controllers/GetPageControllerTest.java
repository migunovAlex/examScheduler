package com.examscheduler.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.ModelMap;

import com.examscheduler.controllers.tools.CookieHelper;
import com.examscheduler.security.service.UserDetailService;

public class GetPageControllerTest {
	
	private static final String TEST_USERNAME = "TEST_USERNAME";

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
	@Mock 
	private UserDetails userDetails;

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
	public void shouldGetMainPageForAuthorisedAndNotAddUserDetails(){
		when(cookieHelper.getSessionCookie(request)).thenReturn(FAKE_SESSION);
		when(userDetailService.getUserDetailsBySession(FAKE_SESSION)).thenReturn(null);
		assertEquals(controller.getAuthorizedMainPage(request, modelMap), "mainPage");
		verify(modelMap, times(1)).addAttribute("userSession", FAKE_SESSION);
		verify(modelMap, never()).addAttribute(GetPageController.USER_NAME_PARAM, TEST_USERNAME);
	}
	
	@Test
	public void shouldGetMainPageForAuthorisedAndAddUserDetails(){
		when(cookieHelper.getSessionCookie(request)).thenReturn(FAKE_SESSION);
		when(userDetailService.getUserDetailsBySession(FAKE_SESSION)).thenReturn(userDetails);
		when(userDetails.getUsername()).thenReturn(TEST_USERNAME);
		assertEquals(controller.getAuthorizedMainPage(request, modelMap), "mainPage");
		verify(modelMap, times(1)).addAttribute("userSession", FAKE_SESSION);
		verify(modelMap, times(1)).addAttribute(GetPageController.USER_NAME_PARAM, TEST_USERNAME);
	}
	
	@Test
	public void shouldGetLessonTimePage(){
		when(cookieHelper.getSessionCookie(request)).thenReturn(FAKE_SESSION);
		when(userDetailService.getUserDetailsBySession(FAKE_SESSION)).thenReturn(userDetails);
		when(userDetails.getUsername()).thenReturn(TEST_USERNAME);
		assertEquals(controller.getLessonTimePage(request, modelMap), "lessonTime");
		verify(modelMap, times(1)).addAttribute("userSession", FAKE_SESSION);
		verify(modelMap, times(1)).addAttribute(GetPageController.USER_NAME_PARAM, TEST_USERNAME);
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
