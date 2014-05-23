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

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session currentSession(){
		return getSessionFactory().getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	public UserSession getUserSessionByValue(String userSession) {
		List<UserSession> userSessionList = currentSession().createCriteria(UserSession.class).add(Restrictions.eq("sessionValue", userSession)).list();
		if(userSessionList.size()==0) return null;
		return userSessionList.get(0);
	}

	public void updateUserSession(UserSession userSession) {
		// TODO Auto-generated method stub

	}

	public int saveSession(UserSession userSession) throws PersistentActionException {
		Integer savedId = 0;
		try{
			savedId = (Integer) currentSession().save(userSession);
		}catch (HibernateException ex){
			throw new PersistentActionException("Exception while saving session to DB");
		}
		return savedId;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
