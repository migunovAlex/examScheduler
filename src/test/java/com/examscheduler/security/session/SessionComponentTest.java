package com.examscheduler.security.session;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any; 

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.examscheduler.security.persistence.SessionDAO;
import com.examscheduler.security.persistence.entity.UserSession;
import com.examscheduler.security.tools.SessionGenerator;

@RunWith(MockitoJUnitRunner.class)
public class SessionComponentTest {
	
	private static final String FAKE_GENERATED_SESSION = "FAKE_GENERATED_SESSION";

	@Mock
	private SessionGenerator sessionGenerator;
	@Mock
	private Thread mockTread;
	@Mock
	private SessionComponent.TimeThread mockTimeThread;
	@Mock
	public SessionDAO sessionDao;
	
	private SessionComponent testInstance;
	
	@Before
	public void setUp(){
		testInstance = new SessionComponent();
		testInstance.setSessionGenerator(sessionGenerator);
		testInstance.setGetSessionThread(mockTread);
		testInstance.setTimeThread(mockTimeThread);
		testInstance.setNeedToInitialize(Boolean.TRUE);
		testInstance.setSessionDao(sessionDao);
		when(sessionGenerator.generateSession()).thenReturn(FAKE_GENERATED_SESSION);
	}
	
	@Test
	public void shouldReturnGeneratedSession(){
		String generateNewSession = testInstance.generateNewSession();
		assertNotNull(generateNewSession);
	}
	
	@Test
	public void shouldSessionActivityUpdate(){
		UserSession userSession = generatedUserSession();
		when(sessionDao.getUserSessionByValue(any(String.class))).thenReturn(userSession);
		when(sessionDao.updateUserSession(userSession)).thenReturn(true);
		Boolean result = testInstance.sessionActivityUpdate(userSession.getSessionValue());
		assertNotNull(result);
		assertEquals(result, true);
	}
	
	@Test
	public void shouldSessionActivityUpdateFalse(){
		UserSession userSessionFalse = generatedUserSession();
		userSessionFalse.setActive(false);
		when(sessionDao.getUserSessionByValue(any(String.class))).thenReturn(userSessionFalse);
		when(sessionDao.updateUserSession(userSessionFalse)).thenReturn(false);
		Boolean result = testInstance.sessionActivityUpdate(userSessionFalse.getSessionValue());
		assertNotNull(result);
		assertEquals(result, false);
	}
	
	@Test
	public void shouldInit(){
	}
	
	@Test
	public void shouldInitialize(){
	}
	
	public UserSession generatedUserSession(){
		UserSession createdUserSession = new UserSession();
		createdUserSession.setId(0);
		createdUserSession.setActive(true);
		createdUserSession.setSessionValue(FAKE_GENERATED_SESSION);
		createdUserSession.setUser(null);
		createdUserSession.setLastActivity(System.currentTimeMillis());
		return createdUserSession;
		
	}
}
