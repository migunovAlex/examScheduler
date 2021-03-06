package com.examscheduler.security.persistence;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.examscheduler.security.persistence.entity.DbUser;
import com.examscheduler.security.persistence.entity.UserSession;

@Transactional
public class UserDAOImpl implements UserDao {
	
	private static final String USERNAME_FIELD_NAME = "username";
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session currentSession(){
		return getSessionFactory().getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public DbUser searchDatabase(String username) {
		List<DbUser> users=null;
		users = currentSession().createCriteria(DbUser.class).add(Restrictions.eq(USERNAME_FIELD_NAME, username)).list();
		if(users.size()==0) return null;
		return users.get(0);
	}
	
	public DbUser getUserBySessionValue(String sessionValue){
		UserSession userSession = getSession(sessionValue);
		if(userSession == null) return null;
		return userSession.getUser();
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public UserSession getUserSession(String sessionValue) {
		return getSession(sessionValue);
	}
	
	@SuppressWarnings("unchecked")
	private UserSession getSession(String sessionValue){
		List<UserSession> userSessionList = currentSession().createCriteria(UserSession.class).add(Restrictions.eq("sessionValue", sessionValue)).list();
		if(userSessionList.size() == 0) return null;
		UserSession userSession = userSessionList.get(0);
		Hibernate.initialize(userSession.getUser());
		return userSession;
		
	}
	
}
