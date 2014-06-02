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
import com.examscheduler.dto.SessionDTO;
import com.examscheduler.exceptions.PersistentActionException;
import com.examscheduler.security.persistence.SessionDAO;
import com.examscheduler.security.persistence.entity.DbUser;
import com.examscheduler.security.persistence.entity.UserSession;
import com.examscheduler.security.session.SessionComponent;
import com.examscheduler.summary.OperationResultSummary;
import com.examscheduler.summary.SessionSummary;

@RunWith(MockitoJUnitRunner.class)
public class SessionServiceImplTest {
	
	private static final String FAKE_SESSION = "FAKE_SESSION";
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
	
	@Test
	public void shouldNotUpdateSessionActivityOnNullSessionValue(){
		SessionDTO session = createSessionDTO();
		session.setSessionValue(null);
		OperationResultSummary updateSessionActivity = testInstance.updateSessionActivity(session);
		assertNotNull(updateSessionActivity);
		assertEquals(updateSessionActivity.getErrorData().getNumberCode(), ErrorData.WRONG_PARAMETERS_IN_REQUEST_CODE);
		assertEquals(updateSessionActivity.getErrorData().getDescription(), ErrorData.WRONG_PARAMETERS_IN_REQUEST_MESSAGE);
	}
	
	@Test
	public void shouldNotUpdateSessionActivityOnNotFoundSessionValueInDB(){
		SessionDTO session = createSessionDTO();
		session.setSessionValue(FAKE_SESSION);
		when(sessionDAO.getUserSessionByValue(FAKE_SESSION)).thenReturn(null);
		OperationResultSummary updateSessionActivity = testInstance.updateSessionActivity(session);
		assertNotNull(updateSessionActivity);
		assertEquals(updateSessionActivity.getErrorData().getNumberCode(), ErrorData.NO_SUCH_SESSION_CODE);
		assertEquals(updateSessionActivity.getErrorData().getDescription(), ErrorData.NO_SUCH_SESSION_MESSAGE);
	}
	
	@Test
	public void shouldNotUpdateSessionActivityOnExpiredSession(){
		SessionDTO session = createSessionDTO();
		session.setSessionValue(FAKE_SESSION);
		UserSession userSession = createUserSession();
		userSession.setActive(false);
		when(sessionDAO.getUserSessionByValue(FAKE_SESSION)).thenReturn(userSession);
		OperationResultSummary updateSessionActivity = testInstance.updateSessionActivity(session);
		assertNotNull(updateSessionActivity);
		assertEquals(updateSessionActivity.getErrorData().getNumberCode(), ErrorData.EXPIRED_SESSION_CODE);
		assertEquals(updateSessionActivity.getErrorData().getDescription(), ErrorData.EXPIRED_SESSION_MESSAGE);
	}
	
	@Test
	public void shouldNotUpdateSessionActivityOnDBException(){
		SessionDTO session = createSessionDTO();
		session.setSessionValue(FAKE_SESSION);
		UserSession userSession = createUserSession();
		userSession.setActive(true);
		when(sessionDAO.getUserSessionByValue(FAKE_SESSION)).thenReturn(userSession);
		when(sessionDAO.updateUserSession(userSession)).thenReturn(false);
		OperationResultSummary updateSessionActivity = testInstance.updateSessionActivity(session);
		assertNotNull(updateSessionActivity);
		assertEquals(updateSessionActivity.getErrorData().getNumberCode(), ErrorData.ERROR_WHILE_OPERATE_WITH_DB_CODE);
		assertEquals(updateSessionActivity.getErrorData().getDescription(), ErrorData.ERROR_WHILE_OPERATE_WITH_DB_MESSAGE);
	}
	
	@Test
	public void shouldUpdateSessionActivity(){
		SessionDTO session = createSessionDTO();
		session.setSessionValue(FAKE_SESSION);
		UserSession userSession = createUserSession();
		userSession.setActive(true);
		when(sessionDAO.getUserSessionByValue(FAKE_SESSION)).thenReturn(userSession);
		when(sessionDAO.updateUserSession(userSession)).thenReturn(true);
		OperationResultSummary updateSessionActivity = testInstance.updateSessionActivity(session);
		assertNotNull(updateSessionActivity);
		assertEquals(updateSessionActivity.getErrorData().getNumberCode(), ErrorData.OK_CODE);
		assertEquals(updateSessionActivity.getErrorData().getDescription(), ErrorData.OK_MESSAGE);
		assertEquals(updateSessionActivity.isOperationResult(), true);
	}
	
	@Test
	public void shouldNotGetCurrentSessionOnNullSessionValue(){
		SessionSummary currentSession = testInstance.getCurrentSession(null);
		assertNotNull(currentSession);
		assertEquals(currentSession.getErrorData().getNumberCode(), ErrorData.WRONG_PARAMETERS_IN_REQUEST_CODE);
		assertEquals(currentSession.getErrorData().getDescription(), ErrorData.WRONG_PARAMETERS_IN_REQUEST_MESSAGE);
	}
	
	@Test
	public void shouldNotGetCurrentSessionOnNoSessionValue(){
		SessionDTO session = createSessionDTO();
		session.setSessionValue(null);
		SessionSummary currentSession = testInstance.getCurrentSession(session);
		assertNotNull(currentSession);
		assertEquals(currentSession.getErrorData().getNumberCode(), ErrorData.WRONG_PARAMETERS_IN_REQUEST_CODE);
		assertEquals(currentSession.getErrorData().getDescription(), ErrorData.WRONG_PARAMETERS_IN_REQUEST_MESSAGE);
	}
	
	@Test
	public void shouldNotGetCurrentSessionOnNoSessionValueInDB(){
		SessionDTO session = createSessionDTO();
		when(sessionDAO.getUserSessionByValue(FAKE_SESSION)).thenReturn(null);
		SessionSummary currentSession = testInstance.getCurrentSession(session);
		assertNotNull(currentSession);
		assertEquals(currentSession.getErrorData().getNumberCode(), ErrorData.NO_SUCH_SESSION_CODE);
		assertEquals(currentSession.getErrorData().getDescription(), ErrorData.NO_SUCH_SESSION_MESSAGE);
	}
	
	@Test
	public void shouldGetCurrentSession(){
		SessionDTO session = createSessionDTO();
		UserSession userSession = createUserSession();
		when(sessionDAO.getUserSessionByValue(FAKE_SESSION)).thenReturn(userSession);
		SessionSummary currentSession = testInstance.getCurrentSession(session);
		assertNotNull(currentSession);
		assertEquals(currentSession.getErrorData().getNumberCode(), ErrorData.OK_CODE);
		assertEquals(currentSession.getErrorData().getDescription(), ErrorData.OK_MESSAGE);
		assertNotNull(currentSession.getSession());
	}
	

	private UserSession createUserSession() {
		UserSession userSession = new UserSession();
		userSession.setActive(true);
		userSession.setId(1);
		userSession.setLastActivity(1L);
		userSession.setSessionValue(FAKE_SESSION);
		userSession.setUser(createNewUser());
		return userSession;
	}

	private SessionDTO createSessionDTO() {
		SessionDTO result = new SessionDTO();
		result.setActive(true);
		result.setLastActivity(1L);
		result.setSessionValue(FAKE_SESSION);
		return result;
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
