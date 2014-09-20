package com.examscheduler.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import com.examscheduler.dto.ErrorData;
import com.examscheduler.security.service.SessionService;
import com.examscheduler.summary.OperationResultSummary;

@RunWith(MockitoJUnitRunner.class)
public class SessionControllerTest {
	
	private static final String SESSION_VALUE = "FAKE_SESSION_VALUE";
	@Mock
	private CookieHelper cookieHelper;
	@Mock
	private SessionService sessionService;
	@Mock
	private HttpServletRequest request;
	
	private SessionController testInstance;
	
	@Before
	public void setUp(){
		testInstance = new SessionController();
		testInstance.setCookieHelper(cookieHelper);
		testInstance.setSessionService(sessionService);
	}
	
	@Test
	public void shouldLogoutSucessfully() throws IOException{
		when(cookieHelper.getSessionCookie(request)).thenReturn(SESSION_VALUE);
		when(sessionService.logout(SESSION_VALUE)).thenReturn(generateOperationResultSummary());
		OperationResultSummary logout = testInstance.logout(request);
		assertNotNull(logout);
		assertEquals(logout.isOperationResult(), Boolean.TRUE);
		assertEquals(ErrorData.OK_CODE, logout.getErrorData().getNumberCode());
		assertEquals(ErrorData.OK_MESSAGE, logout.getErrorData().getDescription());
	}

	private OperationResultSummary generateOperationResultSummary() {
		OperationResultSummary result = new OperationResultSummary();
		result.setOperationResult(Boolean.TRUE);
		return result;
	}

}
