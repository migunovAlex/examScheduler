package com.examscheduler.security.persistence;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
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

public class UserDAOImplTest {
	
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
	
	private DbUser createUser(int access, String username) {
		DbUser userToFound = new DbUser();
		userToFound.setId(1);
		userToFound.setUsername(username);
		userToFound.setAccess(access);
		userToFound.setPassword(DEF_PASSWORD);
		return userToFound;
	}

}
