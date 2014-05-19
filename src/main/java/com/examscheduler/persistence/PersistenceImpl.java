package com.examscheduler.persistence;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.examscheduler.entity.Auditorie;
import com.examscheduler.entity.LessonsTime;
import com.examscheduler.persistence.PersistenceDAO;

@Transactional
public class PersistenceImpl implements PersistenceDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private Session currentSession(){
		System.out.println("sessionFactory: " + sessionFactory);
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
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean deleteAuditories(Auditorie auditorie) {
		try{
			currentSession().delete(auditorie);
		}catch(HibernateException e){
			//e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean updateAuditories(Auditorie auditorie) {
		try{
			currentSession().update(auditorie);
		}catch(HibernateException e){
			e.printStackTrace();
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
			e.printStackTrace();
		}
		return auditorie;
	}
	
	@SuppressWarnings("unchecked")
	public List<Auditorie> getListAuditorie(){
		List<Auditorie> listAuditorie = null;
		try{
			listAuditorie = currentSession().createCriteria(Auditorie.class).list();
		}catch(HibernateException e){
			e.printStackTrace();
		}
		return listAuditorie;
	}
	
	public boolean createLessonsTime(LessonsTime lessonsTime){
		try{
			currentSession().save(lessonsTime);
		}catch(HibernateException e){
			return false;
		}
		return true;
	}

	public boolean deleteLessonsTime(LessonsTime lessonsTime) {
		try{
			currentSession().delete(lessonsTime);
		}catch(HibernateException e){
			return false;
		}
		return true;
	}

	public boolean updateLessonsTime(LessonsTime lessonsTime) {
		try{
			currentSession().update(lessonsTime);
		}catch(HibernateException e){
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
			e.printStackTrace();
		}
		return lessonsTime;
	}
	
	@SuppressWarnings("unchecked")
	public List<LessonsTime> getListLessonTime(){
		List<LessonsTime> listLessonTime = null;
		try{
			listLessonTime = currentSession().createCriteria(LessonsTime.class).list();
		}catch(HibernateException e){
			e.printStackTrace();
		}
		return listLessonTime;
	}
	
}
