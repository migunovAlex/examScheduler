package com.examscheduler.security.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import com.examscheduler.security.persistence.UserDao;
import com.examscheduler.security.persistence.entity.DbUser;
import com.examscheduler.security.persistence.entity.UserSession;

public class UserDetailServiceTest {
	
	private static final String FAKE_SESSION = "FAKE_SESSION";

	private static final String ROLE_MANAGER = "ROLE_MANAGER";

	private static final String MANAGER_USER = "manager";

	private static final String ROLE_ADMIN = "ROLE_ADMIN";

	private static final String DEF_PASSWORD = "123123";

	private static final String ROOT_USER = "root";

	@Mock
	private UserDao userDao;
	private UserDetailService testInstance;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		testInstance = new UserDetailService();
		testInstance.setUserDao(userDao);
	}
	
	@Test
	public void shouldFoundAdminUser(){
		DbUser adminUser = createUser(1, ROOT_USER);
		when(userDao.searchDatabase(ROOT_USER)).thenReturn(adminUser);
		UserDetails userDetails = testInstance.loadUserByUsername(ROOT_USER);
		assertNotNull(userDetails);
		assertEquals(userDetails.getUsername(),ROOT_USER);
		assertEquals(userDetails.getPassword().toLowerCase(), DEF_PASSWORD);
		assertNotNull(userDetails.getAuthorities());
		assertEquals(userDetails.getAuthorities().isEmpty(), false);
		assertEquals(userDetails.getAuthorities().iterator().next().getAuthority(), ROLE_ADMIN);
	}
	
	@Test
	public void shouldFoundManagerUser(){
		DbUser adminUser = createUser(2, MANAGER_USER);
		when(userDao.searchDatabase(MANAGER_USER)).thenReturn(adminUser);
		UserDetails userDetails = testInstance.loadUserByUsername(MANAGER_USER);
		assertNotNull(userDetails);
		assertEquals(userDetails.getUsername(),MANAGER_USER);
		assertEquals(userDetails.getPassword().toLowerCase(), DEF_PASSWORD);
		assertNotNull(userDetails.getAuthorities());
		assertEquals(userDetails.getAuthorities().isEmpty(), false);
		assertEquals(userDetails.getAuthorities().iterator().next().getAuthority(), ROLE_MANAGER);
	}
	
	@Test
	public void shouldNotGetUserDetailsBySession(){
		when(userDao.getUserBySessionValue(FAKE_SESSION)).thenReturn(null);
		UserDetails userDetailsBySession = testInstance.getUserDetailsBySession(FAKE_SESSION);
		assertNull(userDetailsBySession);
	}
	
	@Test
	public void shouldGetUserDetailsBySession(){
		when(userDao.getUserBySessionValue(FAKE_SESSION)).thenReturn(createUser(1, ROOT_USER));
		UserDetails userDetailsBySession = testInstance.getUserDetailsBySession(FAKE_SESSION);
		assertNotNull(userDetailsBySession);
	}
	
	@Test
	public void shouldReturnUserIsNotLoggedInWhenNoSessionInDB(){
		when(userDao.getUserSession(FAKE_SESSION)).thenReturn(null);
		boolean userStillLoggedIn = testInstance.isUserStillLoggedIn(FAKE_SESSION);
		assertEquals(Boolean.FALSE, userStillLoggedIn);
	}
	
	@Test
	public void shouldReturnUserIsNoLoggedInWhenSessionIsNotActive(){
		UserSession userDetailsWithNotActiveSession = createUserDetails(Boolean.FALSE);
		when(userDao.getUserSession(FAKE_SESSION)).thenReturn(userDetailsWithNotActiveSession);
		boolean userStillLoggedIn = testInstance.isUserStillLoggedIn(FAKE_SESSION);
		assertEquals(Boolean.FALSE, userStillLoggedIn);
	}
	
	@Test
	public void shouldReturnUserIsLoggedInWhenSessionIsActive(){
		UserSession userDetailsWithNotActiveSession = createUserDetails(Boolean.TRUE);
		when(userDao.getUserSession(FAKE_SESSION)).thenReturn(userDetailsWithNotActiveSession);
		boolean userStillLoggedIn = testInstance.isUserStillLoggedIn(FAKE_SESSION);
		assertEquals(Boolean.TRUE, userStillLoggedIn);
	}
	
	private UserSession createUserDetails(boolean isActive) {
		UserSession userSession = new UserSession();
		userSession.setActive(isActive);
		userSession.setId(1);
		userSession.setLastActivity(new DateTime().getMillis());
		userSession.setSessionValue(FAKE_SESSION);
		return userSession;
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
