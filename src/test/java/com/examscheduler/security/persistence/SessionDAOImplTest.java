package com.examscheduler.security.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.SimpleExpression;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.examscheduler.exceptions.PersistentActionException;
import com.examscheduler.security.persistence.entity.DbUser;
import com.examscheduler.security.persistence.entity.UserSession;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class SessionDAOImplTest {
	
	private static final long SESSION_ID = 1L;
	private static final String SOMETHING_WRONG = "SOMETHING_WRONG";
	private static final String FAKE_USERNAME = "FAKE_USERNAME";
	private static final String FAKE_PASSWORD = "FAKE_PASSWORD";
	private static final String FAKE_SESSION_VALUE = "FAKE_SESSION_VALUE";
	@Mock
	private SessionFactory sessionFactory;
	@Mock
	private Session session;
	@Mock
	private Criteria criteria;
	@Mock
	private Criteria criteriaForGetList;
	@Mock
	private SimpleExpression expression;
	
	private SessionDAOImpl testInstance;
	
	@Before
	public void setUp(){
		when(sessionFactory.getCurrentSession()).thenReturn(session);
		testInstance = new SessionDAOImpl();
		testInstance.setSessionFactory(sessionFactory);
	}
	
	@Test
	public void shouldGetUserSessionByValue(){
		when(session.createCriteria(UserSession.class)).thenReturn(criteria);
		when(criteria.add(any(Criterion.class))).thenReturn(criteriaForGetList);
		when(criteriaForGetList.list()).thenReturn(getSessionList());
		UserSession userSessionByValue = testInstance.getUserSessionByValue(FAKE_SESSION_VALUE);
		assertNotNull(userSessionByValue);
		assertEquals(userSessionByValue.getSessionValue(), FAKE_SESSION_VALUE);
	}
	
	@Test
	public void shouldReturnNullWhenNoSessionFound(){
		when(session.createCriteria(UserSession.class)).thenReturn(criteria);
		when(criteria.add(any(Criterion.class))).thenReturn(criteriaForGetList);
		when(criteriaForGetList.list()).thenReturn(Collections.emptyList());
		UserSession userSessionByValue = testInstance.getUserSessionByValue(FAKE_SESSION_VALUE);
		assertNull(userSessionByValue);
	}
	
	@Test
	public void shouldUpdateSessionSuccessfully(){
		UserSession singleUserSession = getSingleUserSession();
		boolean updateUserSession = testInstance.updateUserSession(singleUserSession);
		verify(session, times(1)).update(singleUserSession);
		assertEquals(updateUserSession, true);
	}
	
	@Test
	public void shouldNotUpdateSessionSuccessfully(){
		UserSession singleUserSession = getSingleUserSession();
		doThrow(new HibernateException(SOMETHING_WRONG)).when(session).update(singleUserSession);
		boolean updateUserSession = testInstance.updateUserSession(singleUserSession);
		assertEquals(updateUserSession, false);
	}
	
	@Test
	public void shouldSaveSessionSuccessfully() throws PersistentActionException{
		UserSession singleUserSession = getSingleUserSession();
		when(session.save(singleUserSession)).thenReturn(SESSION_ID);
		Long savedSessionId = testInstance.saveSession(singleUserSession);
		assertNotNull(savedSessionId);
		assertEquals(savedSessionId.longValue(), SESSION_ID);
	}
	
	@Test(expected=PersistentActionException.class)
	public void shouldNotSaveSessionSuccessfullyOnThrowException() throws PersistentActionException{
		UserSession singleUserSession = getSingleUserSession();
		when(session.save(singleUserSession)).thenThrow(new HibernateException("Exception while saving session"));
		testInstance.saveSession(singleUserSession);
	}

	private UserSession getSingleUserSession() {
		UserSession userSession = new UserSession();
		userSession.setActive(true);
		userSession.setId(1);
		userSession.setLastActivity(0L);
		userSession.setSessionValue(FAKE_SESSION_VALUE);
		userSession.setUser(getUser());
		return userSession;
	}

	private List<UserSession> getSessionList() {
		List<UserSession> userSessionsList = new ArrayList<UserSession>();
		UserSession userSession = new UserSession();
		userSession.setId(1);
		userSession.setActive(true);
		userSession.setLastActivity(100L);
		userSession.setSessionValue(FAKE_SESSION_VALUE);
		DbUser fakeUser = getUser();
		userSession.setUser(fakeUser );
		userSessionsList.add(userSession);
		return userSessionsList;
	}

	private DbUser getUser() {
		DbUser fakeUser = new DbUser();
		fakeUser.setId(1);
		fakeUser.setAccess(1);
		fakeUser.setPassword(FAKE_PASSWORD);
		fakeUser.setUsername(FAKE_USERNAME);
		return fakeUser;
	}
	
	

}
