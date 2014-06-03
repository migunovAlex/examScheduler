package com.examscheduler.security.request.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;

import com.examscheduler.dto.SessionDTO;
import com.examscheduler.security.persistence.UserDao;
import com.examscheduler.security.persistence.entity.DbUser;
import com.examscheduler.security.service.SessionService;
import com.examscheduler.summary.SessionSummary;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class PostAuthenticationFilterTest {

	private static final String FAKE_SESSION_VALUE = "FAKE_SESSION_VALUE";
	private static final String USERNAME = "USERNAME";
	private static final String PASSWORD = "PASSWORD";
	@Mock
	private UserDao userDao;
	@Mock
	private SessionService sessionService;
	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private Authentication auth;
	
	private PostAuthenticationFilter testInstance;
	
	@Before
	public void setUp(){
		testInstance = new PostAuthenticationFilter();
		testInstance.setSessionService(sessionService);
		testInstance.setUserDao(userDao);
	}
	
	@Test
	public void shouldNotAddSessionParamOnNullParameters() throws IOException, ServletException{
		when(request.getParameter(PostAuthenticationFilter.USERNAME_PARAM)).thenReturn(null);
		when(request.getParameter(PostAuthenticationFilter.PASSWORD_PARAM)).thenReturn(null);
		testInstance.onAuthenticationSuccess(request, response, auth);
		verify(userDao, never()).searchDatabase(any(String.class));
	}
	
	@Test
	public void shouldNotAddSessionParamOnNullUsername() throws IOException, ServletException{
		when(request.getParameter(PostAuthenticationFilter.USERNAME_PARAM)).thenReturn(USERNAME);
		when(request.getParameter(PostAuthenticationFilter.PASSWORD_PARAM)).thenReturn(null);
		testInstance.onAuthenticationSuccess(request, response, auth);
		verify(userDao, never()).searchDatabase(any(String.class));
	}
	
	@Test
	public void shouldNotAddSessionParamOnUserNotFound() throws IOException, ServletException{
		when(request.getParameter(PostAuthenticationFilter.USERNAME_PARAM)).thenReturn(USERNAME);
		when(request.getParameter(PostAuthenticationFilter.PASSWORD_PARAM)).thenReturn(PASSWORD);
		when(userDao.searchDatabase(USERNAME)).thenReturn(null);
		testInstance.onAuthenticationSuccess(request, response, auth);
		verify(sessionService, never()).getNewSession(any(DbUser.class));
	}
	
	@Test
	public void shouldNotGenerateSessionParam() throws IOException, ServletException{
		when(request.getParameter(PostAuthenticationFilter.USERNAME_PARAM)).thenReturn(USERNAME);
		when(request.getParameter(PostAuthenticationFilter.PASSWORD_PARAM)).thenReturn(PASSWORD);
		SessionSummary sessionSummary = getSessionSummary();
		sessionSummary.setSession(null);
		DbUser user = getUser();
		when(userDao.searchDatabase(USERNAME)).thenReturn(user);
		when(sessionService.getNewSession(user)).thenReturn(sessionSummary);
		testInstance.onAuthenticationSuccess(request, response, auth);
		verify(response, never()).addCookie(any(Cookie.class));
	}
	
	@Test
	public void shouldGenerateSessionParam() throws IOException, ServletException{
		when(request.getParameter(PostAuthenticationFilter.USERNAME_PARAM)).thenReturn(USERNAME);
		when(request.getParameter(PostAuthenticationFilter.PASSWORD_PARAM)).thenReturn(PASSWORD);
		SessionSummary sessionSummary = getSessionSummary();
		DbUser user = getUser();
		when(userDao.searchDatabase(USERNAME)).thenReturn(user);
		when(sessionService.getNewSession(user)).thenReturn(sessionSummary);
		testInstance.onAuthenticationSuccess(request, response, auth);
		verify(response, times(1)).addCookie(any(Cookie.class));
	}
	
	private DbUser getUser() {
		DbUser user = new DbUser();
		user.setAccess(1);
		user.setId(1);
		user.setPassword(PASSWORD);
		user.setUsername(USERNAME);
		return user;
	}

	private SessionSummary getSessionSummary(){
		SessionSummary result = new SessionSummary();
		result.setSession(getSessionDTO());
		return result;
	}

	private SessionDTO getSessionDTO() {
		SessionDTO result = new SessionDTO();
		result.setActive(true);
		result.setLastActivity(1L);
		result.setSessionValue(FAKE_SESSION_VALUE);
		return result;
	}
	
}
