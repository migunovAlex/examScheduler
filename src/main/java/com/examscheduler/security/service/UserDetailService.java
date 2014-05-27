package com.examscheduler.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.examscheduler.security.persistence.UserDao;
import com.examscheduler.security.persistence.entity.DbUser;

@SuppressWarnings("deprecation")
public class UserDetailService implements UserDetailsService{
	
	private static final String ROLE_ADMIN = "ROLE_ADMIN";
	private static final String ROLE_MANAGER = "ROLE_MANAGER";
	private static final String ROLE_USER = "ROLE_USER";

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private SessionService sessionService;

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		UserDetails user =null;
		DbUser dbUser = null;
		dbUser = userDao.searchDatabase(username);
		
		user = new User(dbUser.getUsername(),
				dbUser.getPassword().toLowerCase(),
				true,
				true,
				true,
				true,
				getAuthorities(dbUser.getAccess()));
		
		getSessionService().getNewSession(dbUser);
		
		return user;
	}

	private Collection<GrantedAuthority> getAuthorities(Integer access) {
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
		
		authList.add(new GrantedAuthorityImpl(ROLE_USER));
		if(access.equals(2)) authList.add(new GrantedAuthorityImpl(ROLE_MANAGER));
		if(access.equals(1)) authList.add(new GrantedAuthorityImpl(ROLE_ADMIN));
		
		return authList;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public SessionService getSessionService() {
		return sessionService;
	}

	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}

}
