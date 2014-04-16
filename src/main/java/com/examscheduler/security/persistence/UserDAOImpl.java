package com.examscheduler.security.persistence;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.examscheduler.security.persistence.entity.DbUser;

@Transactional
public class UserDAOImpl implements UserDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session currentSession(){
		return getSessionFactory().getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public DbUser searchDatabase(String username) {
		List<DbUser> users=null;
		users = currentSession().createCriteria(DbUser.class).add(Restrictions.eq("username", username)).list();
		if(users.size()==0) return null;
		return users.get(0);
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
