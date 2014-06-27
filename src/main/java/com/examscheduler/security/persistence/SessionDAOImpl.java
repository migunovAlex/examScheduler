package com.examscheduler.security.persistence;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.examscheduler.exceptions.PersistentActionException;
import com.examscheduler.security.persistence.entity.UserSession;

@Transactional
public class SessionDAOImpl implements SessionDAO {

	protected static final String SESSION_VALUE = "sessionValue";
	protected static final String ACTIVE = "active";
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session currentSession(){
		return getSessionFactory().getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	public UserSession getUserSessionByValue(String userSession) {
		List<UserSession> userSessionList = currentSession().createCriteria(UserSession.class).add(Restrictions.eq(SESSION_VALUE, userSession)).list();
		if(userSessionList.size()==0) return null;
		return userSessionList.get(0);
	}

	public boolean updateUserSession(UserSession userSession) {
		try{
			currentSession().update(userSession);
		}catch(HibernateException e){
			return false;
		}
		return true;
	}

	public Long saveSession(UserSession userSession) throws PersistentActionException {
		Long savedId = 0L;
		try{
			savedId = (Long) currentSession().save(userSession);
		}catch (HibernateException ex){
			throw new PersistentActionException("Exception while saving session to DB");
		}
		return savedId;
	}
	
	public List<UserSession> getUserSessionIsActive(){
		@SuppressWarnings("unchecked")
		List<UserSession> listUserSession = currentSession().createCriteria(UserSession.class).add(Restrictions.eq(ACTIVE, true)).list();
		return listUserSession;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
