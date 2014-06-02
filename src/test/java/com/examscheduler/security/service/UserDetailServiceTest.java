package com.examscheduler.security.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import com.examscheduler.security.persistence.UserDao;
import com.examscheduler.security.persistence.entity.DbUser;

public class UserDetailServiceTest {
	
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
	
	private DbUser createUser(int access, String username) {
		DbUser userToFound = new DbUser();
		userToFound.setId(1);
		userToFound.setUsername(username);
		userToFound.setAccess(access);
		userToFound.setPassword(DEF_PASSWORD);
		return userToFound;
	}

}
