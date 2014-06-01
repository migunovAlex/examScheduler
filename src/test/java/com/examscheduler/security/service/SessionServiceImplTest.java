package com.examscheduler.security.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.examscheduler.dto.ErrorData;
import com.examscheduler.exceptions.PersistentActionException;
import com.examscheduler.security.persistence.SessionDAO;
import com.examscheduler.security.persistence.entity.DbUser;
import com.examscheduler.security.persistence.entity.UserSession;
import com.examscheduler.security.session.SessionComponent;
import com.examscheduler.summary.SessionSummary;

@RunWith(MockitoJUnitRunner.class)
public class SessionServiceImplTest {
	
	private static final String TEST_PASSWORD = "TEST_PASSWORD";
	private static final String TEST_USER = "TEST_USER";
	@Mock
	private SessionComponent sessionComponent;
	@Mock
	private SessionDAO sessionDAO;
	
	private SessionServiceImpl testInstance;
	
	@Before
	public void setUp(){
		testInstance = new SessionServiceImpl();
		testInstance.setSessionComponent(sessionComponent);
		testInstance.setSessionDao(sessionDAO);
	}
	
	@Test
	public void shouldNotGetNewSessionOnNullUser(){
		SessionSummary newSession = testInstance.getNewSession(null);
		assertNotNull(newSession);
		assertEquals(newSession.getErrorData().getNumberCode(), ErrorData.WRONG_PARAMETERS_IN_REQUEST_CODE);
		assertEquals(newSession.getErrorData().getDescription(), ErrorData.WRONG_PARAMETERS_IN_REQUEST_MESSAGE);
	}
	
	@Test
	public void shouldNotGetNewSessionOnUserWithoutID(){
		DbUser testUser = createNewUser();
		testUser.setId(0);
		SessionSummary newSession = testInstance.getNewSession(testUser);
		assertNotNull(newSession);
		assertEquals(newSession.getErrorData().getNumberCode(), ErrorData.WRONG_PARAMETERS_IN_REQUEST_CODE);
		assertEquals(newSession.getErrorData().getDescription(), ErrorData.WRONG_PARAMETERS_IN_REQUEST_MESSAGE);
	}
	
	@Test
	public void shouldGetNewSessionSuccessfully() throws PersistentActionException{
		DbUser generateUser = createNewUser();
		when(sessionDAO.saveSession(any(UserSession.class))).thenReturn(1L);
		SessionSummary newSession = testInstance.getNewSession(generateUser);
		assertNotNull(newSession);
		assertEquals(newSession.getErrorData().getNumberCode(), ErrorData.OK_CODE);
		assertEquals(newSession.getSession().isActive(), true);
	}
	
	@Test
	public void shouldNotGetNewSessionOnPersistenceException() throws PersistentActionException{
		DbUser testUser = createNewUser();
		when(sessionDAO.saveSession(any(UserSession.class))).thenThrow(new PersistentActionException("TEST EXCEPTION"));
		SessionSummary newSession = testInstance.getNewSession(testUser);
		assertNotNull(newSession);
		assertEquals(newSession.getErrorData().getNumberCode(), ErrorData.ERROR_WHILE_OPERATE_WITH_DB_CODE);
		assertEquals(newSession.getErrorData().getDescription(), ErrorData.ERROR_WHILE_OPERATE_WITH_DB_MESSAGE);
	}

	private DbUser createNewUser() {
		DbUser resultUser = new DbUser();
		resultUser.setId(1);
		resultUser.setAccess(1);
		resultUser.setUsername(TEST_USER);
		resultUser.setPassword(TEST_PASSWORD);
		return resultUser;
	}

}
