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

import com.examscheduler.entity.Auditorie;
import com.examscheduler.entity.LessonsTime;

@Transactional
public class PersistenceImpl implements PersistenceDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	Logger logger = Logger.getLogger(PersistenceImpl.class);

	private Session currentSession(){
		return getSessionFactory().getCurrentSession();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public boolean createAuditories(Auditorie auditorie) {
		try{
			currentSession().save(auditorie);
		}catch(HibernateException e){
			logger.error("Error while saving auditorie - " + auditorie, e);
			return false;
		}
		return true;
	}

	public boolean deleteAuditories(Auditorie auditorie) {
		try{
			currentSession().delete(auditorie);
		}catch(HibernateException e){
			logger.error("Error while deleting auditorie - " + auditorie, e);
			return false;
		}
		return true;
	}

	public boolean updateAuditories(Auditorie auditorie) {
		try{
			currentSession().update(auditorie);
		}catch(HibernateException e){
			logger.error("Error while updating auditorie - " + auditorie, e);
			return false;
		}
		return true;
	}

	public Auditorie loadAuditorie(Integer auditorieId) {
		Auditorie auditorie = null;
		try{
			auditorie = (Auditorie) currentSession().load(Auditorie.class, auditorieId);
			Hibernate.initialize(auditorie);
		}catch(HibernateException e){
			logger.error("Error while loading auditorie - " + auditorie, e);
		}
		return auditorie;
	}
	
	@SuppressWarnings("unchecked")
	public List<Auditorie> getListAuditorie(){
		List<Auditorie> listAuditorie = null;
		try{
			listAuditorie = currentSession().createCriteria(Auditorie.class).list();
		}catch(HibernateException e){
			logger.error("Error while loading list of auditories", e);
		}
		return listAuditorie;
	}
	
	public boolean createLessonsTime(LessonsTime lessonsTime){
		try{
			currentSession().save(lessonsTime);
		}catch(HibernateException e){
			logger.error("Error while creating lessonsTime - " + lessonsTime, e);
			return false;
		}
		return true;
	}

	public boolean deleteLessonsTime(LessonsTime lessonsTime) {
		try{
			currentSession().delete(lessonsTime);
		}catch(HibernateException e){
			logger.error("Exception while deleting LessonsTime - " + lessonsTime);
			return false;
		}
		return true;
	}

	public boolean updateLessonsTime(LessonsTime lessonsTime) {
		try{
			currentSession().update(lessonsTime);
		}catch(HibernateException e){
			logger.error("Exception while updating LessonsTime - " + lessonsTime);
			return false;
		}
		return true;
	}

	public LessonsTime loadLessonsTime(Integer lessonsTimeId) {
		LessonsTime lessonsTime = null;
		try{
			lessonsTime = (LessonsTime) currentSession().load(LessonsTime.class, lessonsTimeId);
			Hibernate.initialize(lessonsTime);
		}catch(HibernateException e){
			logger.error("Exception while getting LessonsTime with ID - " + lessonsTimeId);
		}
		return lessonsTime;
	}
	
	@SuppressWarnings("unchecked")
	public List<LessonsTime> getListLessonTime(){
		List<LessonsTime> listLessonTime = null;
		try{
			listLessonTime = currentSession().createCriteria(LessonsTime.class).list();
		}catch(HibernateException e){
			logger.error("Exception while getting List of LessonsTime");
		}
		if(listLessonTime == null)
			return Collections.EMPTY_LIST;
		return listLessonTime;
	}
	
}
