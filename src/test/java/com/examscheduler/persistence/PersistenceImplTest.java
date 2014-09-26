package com.examscheduler.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.examscheduler.entity.Auditory;
import com.examscheduler.entity.LessonsTime;
import com.examscheduler.exceptions.PersistentActionException;

public class PersistenceImplTest {
	
	private static final int ZERO_LENGTH = 0;

	private static final String TIME_END = "09:30";

	private static final String TIME_START = "08:00";

	private PersistenceImpl persistence;

	@Mock
	public SessionFactory sessionFactory;
	@Mock
	public Session session;
	@Mock
	public Criteria criteria;
	
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		when(sessionFactory.getCurrentSession()).thenReturn(session);
		persistence = new PersistenceImpl();
		persistence.setSessionFactory(sessionFactory);
	}
	
	@Test
	public void shouldCreateLessonsTime(){
		when(session.save(any(LessonsTime.class))).thenReturn(1);
		Boolean createResult = persistence.createLessonsTime(getLessonsTimeClass(12));
		assertEquals(createResult, true);
	}
	
	@Test	
	public void shouldCreateLessonsTimeFalse(){
		doThrow(new HibernateException("Can not save to BD")).when(session).save(any(LessonsTime.class));
		Boolean createResult = persistence.createLessonsTime(getLessonsTimeClass(12));
		assertEquals(createResult, false);
	}
	
	@Test
	public void shouldDeleteLessonsTime(){
		LessonsTime lessonsTimeClass = getLessonsTimeClass(12);
		Boolean resultDelete = persistence.deleteLessonsTime(lessonsTimeClass);
		assertEquals(resultDelete, true);
		verify(session, times(1)).delete(lessonsTimeClass);
	}
	
	@Test
	public void shouldDeleteLessensTimeException(){
		doThrow(new HibernateException("Can not delete from DB")).when(session).delete(any(LessonsTime.class));
		Boolean resultDelete = persistence.deleteLessonsTime(getLessonsTimeClass(12));
		assertEquals(resultDelete, false);
	}
	
	@Test
	public void shouldLoadLessonsTime(){
		LessonsTime lessonsTime = getLessonsTimeClass(12);
		when(session.load(LessonsTime.class, 12)).thenReturn(lessonsTime);
		LessonsTime resultLoad = persistence.loadLessonsTime(12);
		assertEquals(resultLoad, lessonsTime);
	}
	
	@Test
	public void shouldLoadLessonsTimeException(){
		doThrow(new HibernateException("Can not load from DB")).when(session).load(any(LessonsTime.class), any(Integer.class));
		LessonsTime resultLoad = persistence.loadLessonsTime(12);
		assertEquals(resultLoad, null);
	}

	@Test
	public void shouldUpdateLessonsTime(){
		LessonsTime lessonsTimeClass = getLessonsTimeClass(12);
		Boolean resultUpdate = persistence.updateLessonsTime(lessonsTimeClass);
		assertEquals(resultUpdate, true);
		verify(session, times(1)).update(lessonsTimeClass);
	}
	
	@Test
	public void shouldUpdateLessonsTimeException(){
		doThrow(new HibernateException("Can not update to DB")).when(session).update(any(LessonsTime.class));
		Boolean resultUpdate = persistence.updateLessonsTime(getLessonsTimeClass(12));
		assertEquals(resultUpdate,false);
	}
	
	@Test 
	public void shouldGetListLessonTime(){
		List<LessonsTime> listLessonsTime = new ArrayList<LessonsTime>();
		listLessonsTime.add(getLessonsTimeClass(12));
		when(session.createCriteria(any(Class.class))).thenReturn(criteria);
		when(criteria.list()).thenReturn(listLessonsTime);
		List<LessonsTime> resultList = persistence.getListLessonTime();
		assertNotNull(resultList);
		assertEquals(resultList.size(), listLessonsTime.size());
		assertEquals(resultList.get(0).getId(), listLessonsTime.get(0).getId());
		assertEquals(resultList.get(0).getLessonNumber(), listLessonsTime.get(0).getLessonNumber());
		assertEquals(resultList.get(0).getTimeStart(), listLessonsTime.get(0).getTimeStart());
		assertEquals(resultList.get(0).getTimeEnd(), listLessonsTime.get(0).getTimeEnd());
	}
	
	@Test
	public void shouldGetListLessonsTimeException(){
		when(session.createCriteria(any(Class.class))).thenReturn(criteria);
		doThrow(new HibernateException("Can not get list of LessonsTime")).when(criteria).list();
		List <LessonsTime> listResult = persistence.getListLessonTime();
		assertNotNull(listResult);
		assertEquals(ZERO_LENGTH, listResult.size());
	}
	
	public LessonsTime getLessonsTimeClass(int id){
		LessonsTime lessonTime = new LessonsTime();
		lessonTime.setId(id);
		lessonTime.setLessonNumber(5);
		lessonTime.setTimeStart(TIME_START);
		lessonTime.setTimeEnd(TIME_END);
		return lessonTime;
	}
	
	@Test
	public void shouldCreateAuditorie() throws PersistentActionException{
		when(session.save(any(Auditory.class))).thenReturn(1);
		Boolean createResult = persistence.createAuditories(getAuditorie());
		assertEquals(createResult, true);
	}
	
	@Test(expected=HibernateException.class)	
	public void shouldCreateAuditorieException() throws PersistentActionException{
		doThrow(new HibernateException("Can not save to DB")).when(session).save(any(Auditory.class));
		persistence.createAuditories(getAuditorie());
	}
	
	@Test
	public void shouldDeleteAuditorie(){
		Auditory auditorie = getAuditorie();
		Boolean resultDelete = persistence.deleteAuditories(auditorie);
		assertEquals(resultDelete, true);
		verify(session, times(1)).delete(auditorie);
	}
	
	@Test
	public void shouldDeleteAuditorieException(){
		doThrow(new HibernateException("Can not delete from DB")).when(session).delete(any(Auditory.class));
		Boolean resultDelete = persistence.deleteAuditories(getAuditorie());
		assertEquals(resultDelete, false);
	}
	
	 @Test
	  public void shouldReturnEmptyList(){
		when(session.createCriteria(any(Class.class))).thenReturn(criteria);
		when(criteria.list()).thenReturn(null);
		List<LessonsTime> resultList = persistence.getListLessonTime();
		assertNotNull(resultList);
		assertEquals(0, resultList.size());
	}
	
	@Test
	public void shouldLoadAuditorie(){
		Auditory auditorie = getAuditorie();
		when(session.load(Auditory.class, 1)).thenReturn(auditorie);
		Auditory resultLoad = persistence.loadAuditorie(1);
		assertEquals(resultLoad, auditorie);
	}
	
	@Test
	public void shouldLoadAuditorieException(){
		doThrow(new HibernateException("Can not load from DB")).when(session).load(any(Auditory.class), any(Integer.class));
		Auditory resultLoad = persistence.loadAuditorie(1);
		assertEquals(resultLoad, null);
	}

	@Test
	public void shouldUpdateAuditorie(){
		Auditory auditorie = getAuditorie();
		Boolean resultUpdate = persistence.updateAuditories(auditorie);
		assertEquals(resultUpdate, true);
		verify(session, times(1)).update(auditorie);
	}
	
	@Test(expected=HibernateException.class)
	public void shouldUpdateAuditorieException(){
		doThrow(new HibernateException("Can not update to DB")).when(session).update(any(Auditory.class));
		persistence.updateAuditories(getAuditorie());
	}
	
	@Test 
	public void shouldGetListAuditorie(){
		List<Auditory> listAuditorie = new ArrayList<Auditory>();
		listAuditorie.add(getAuditorie());
		when(session.createCriteria(any(Class.class))).thenReturn(criteria);
		when(criteria.list()).thenReturn(listAuditorie);
		List<Auditory> resultList = persistence.getListAuditory();
		assertNotNull(resultList);
		assertEquals(resultList.size(), listAuditorie.size());
		assertEquals(resultList.get(0).getId(), listAuditorie.get(0).getId());
		assertEquals(resultList.get(0).getAudNumber(), listAuditorie.get(0).getAudNumber());
		assertEquals(resultList.get(0).getMaxPerson(), listAuditorie.get(0).getMaxPerson());
	}
	
	@Test
	public void shouldGetEmptyListAuditory(){
		when(session.createCriteria(any(Class.class))).thenReturn(criteria);
		when(criteria.list()).thenReturn(null);
		List<Auditory> resultList = persistence.getListAuditory();
		assertEquals(resultList.size(), 0);
	}
	
	@Test(expected=HibernateException.class)
	public void shouldGetListAuditorieException(){
		when(session.createCriteria(any(Class.class))).thenReturn(criteria);
		doThrow(new HibernateException("Can not get list of LessonsTime")).when(criteria).list();
		persistence.getListAuditory();
	}

	private Auditory getAuditorie() {
		Auditory auditorie = new Auditory();
		auditorie.setId(1);
		auditorie.setAudNumber("01A");
		auditorie.setMaxPerson(40);
		return auditorie;
	}
}
