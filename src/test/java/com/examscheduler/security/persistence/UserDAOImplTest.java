package com.examscheduler.security.persistence;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.SimpleExpression;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.examscheduler.security.persistence.entity.DbUser;
import com.examscheduler.security.persistence.entity.UserSession;

public class UserDAOImplTest {
	
	private static final String FAKE_SESSION = "FAKE_SESSION";
	private static final String ROOT_USER = "root";
	private static final String DEF_PASSWORD = "123123";
	@Mock
	public SessionFactory sessionFactory;
	@Mock 
	public Session session;
	@Mock
	public Criteria criteria;
	@Mock
	public Criteria userNameCriteria;
	@Mock
	public Criteria userSessionCriteria;
	
	public UserDAOImpl userDao;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		when(sessionFactory.getCurrentSession()).thenReturn(session);
		when(session.createCriteria(any(Class.class))).thenReturn(criteria);
		when(criteria.add(any(SimpleExpression.class))).thenReturn(userNameCriteria);
		userDao = new UserDAOImpl();
		userDao.setSessionFactory(sessionFactory);
	}
	
	@Test
	public void shouldFindUser(){
		List<DbUser> userList = new ArrayList<DbUser>();
		userList.add(createUser(1, ROOT_USER));
		when(userNameCriteria.list()).thenReturn(userList);
		DbUser dbUser = userDao.searchDatabase(ROOT_USER);
		assertNotNull(dbUser);
	}
	
	@Test
	public void shouldNotFindUser(){
		List<DbUser> userList = new ArrayList<DbUser>();
		when(userNameCriteria.list()).thenReturn(userList);
		DbUser dbUser = userDao.searchDatabase(ROOT_USER);
		assertNull(dbUser);
	}
	
	@Test
	public void shouldNotGetUserBySessionValue(){
		List<UserSession> userSessionList = new ArrayList<UserSession>();
		when(criteria.add(any(SimpleExpression.class))).thenReturn(userSessionCriteria);
		when(userSessionCriteria.list()).thenReturn(userSessionList);
		DbUser dbUser = userDao.getUserBySessionValue(FAKE_SESSION);
		assertNull(dbUser);
	}
	
	@Test
	public void shouldGetUserBySessionValue(){
		List<UserSession> userSessionList = createUserSessionList();
		when(criteria.add(any(SimpleExpression.class))).thenReturn(userSessionCriteria);
		when(userSessionCriteria.list()).thenReturn(userSessionList);
		DbUser dbUser = userDao.getUserBySessionValue(FAKE_SESSION);
		assertNotNull(dbUser);
	}
	
	private List<UserSession> createUserSessionList() {
		List<UserSession> userSessionList = new ArrayList<UserSession>();
		UserSession userSession = new UserSession();
		userSession.setActive(Boolean.TRUE);
		userSession.setId(1);
		userSession.setLastActivity(Calendar.getInstance().getTimeInMillis());
		userSession.setSessionValue(FAKE_SESSION);
		userSession.setUser(createUser(1,ROOT_USER));
		userSessionList.add(userSession);
		return userSessionList;
	}

	private DbUser createUser(int access, String username) {
		DbUser userToFound = new DbUser();
		userToFound.setId(1);
		userToFound.setUsername(username);
		userToFound.setAccess(access);
		userToFound.setPassword(DEF_PASSWORD);
		return userToFound;
	}

}
