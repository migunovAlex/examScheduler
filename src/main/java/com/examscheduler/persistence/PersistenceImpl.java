package com.examscheduler.persistence;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.examscheduler.entity.Auditory;
import com.examscheduler.entity.LessonsTime;

@Transactional
public class PersistenceImpl implements PersistenceDAO {

	@Autowired
	private SessionFactory sessionFactory;

	Logger logger = Logger.getLogger(PersistenceImpl.class);

	private Session currentSession() {
		return getSessionFactory().getCurrentSession();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public boolean createAuditories(Auditory auditorie) {

		currentSession().save(auditorie);
		return true;
	}

	public boolean deleteAuditories(Auditory auditorie) {
		try {
			currentSession().delete(auditorie);
		} catch (HibernateException e) {
			logger.error("Error while deleting auditorie - " + auditorie, e);
			return false;
		}
		return true;
	}

	public boolean updateAuditories(Auditory auditorie) {

		currentSession().update(auditorie);
		return true;
	}

	public Auditory loadAuditorie(Integer auditorieId) {
		Auditory auditorie = (Auditory) currentSession().load(Auditory.class, auditorieId);

		return auditorie;
	}

	@SuppressWarnings("unchecked")
	public List<Auditory> getListAuditory() {
		List<Auditory> listAuditorie = null;
		
		listAuditorie = currentSession().createCriteria(Auditory.class).list();
		if (listAuditorie == null)
			return Collections.EMPTY_LIST;
		return listAuditorie;
	}
	
	public boolean createLessonsTime(LessonsTime lessonsTime) {
		currentSession().save(lessonsTime);

		return true;
	}

	public boolean deleteLessonsTime(LessonsTime lessonsTime) {
		
		currentSession().delete(lessonsTime);
		return true;
	}

	public boolean updateLessonsTime(LessonsTime lessonsTime) {
		
		currentSession().update(lessonsTime);

		return true;
	}

	public LessonsTime loadLessonsTime(Integer lessonsTimeId) {
		
		LessonsTime lessonsTime = (LessonsTime) currentSession().load(LessonsTime.class, lessonsTimeId);
		Hibernate.initialize(lessonsTime);
		return lessonsTime;
	}

	@SuppressWarnings("unchecked")
	public List<LessonsTime> getListLessonTime() {
		List<LessonsTime> listLessonTime = null;
		
		listLessonTime = currentSession().createCriteria(LessonsTime.class).list();
		if (listLessonTime == null)
			return Collections.EMPTY_LIST;
		return listLessonTime;
	}


	public List<Auditory> getAuditory() {
		// TODO Auto-generated method stub
		return null;
	}

}
