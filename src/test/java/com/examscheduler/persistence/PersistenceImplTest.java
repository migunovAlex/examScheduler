package com.examscheduler.persistence;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import com.examscheduler.entity.Auditorie;
import com.examscheduler.entity.LessonsTime;

public class PersistenceImplTest {
	
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
		assertEquals(listResult, null);
	}
	
	public LessonsTime getLessonsTimeClass(int id){
		LessonsTime lessonTime = new LessonsTime();
		lessonTime.setId(id);
		lessonTime.setLessonNumber(5);
		lessonTime.setTimeStart(1234567890L);
		lessonTime.setTimeEnd(2345678910L);
		return lessonTime;
	}
	
	@Test
	public void shouldCreateAuditorie(){
		when(session.save(any(Auditorie.class))).thenReturn(1);
		Boolean createResult = persistence.createAuditories(getAuditorie());
		assertEquals(createResult, true);
	}
	
	@Test	
	public void shouldCreateAuditorieException(){
		doThrow(new HibernateException("Can not save to BD")).when(session).save(any(Auditorie.class));
		Boolean createResult = persistence.createAuditories(getAuditorie());
		assertEquals(createResult, false);
	}
	
	@Test
	public void shouldDeleteAuditorie(){
		Auditorie auditorie = getAuditorie();
		Boolean resultDelete = persistence.deleteAuditories(auditorie);
		assertEquals(resultDelete, true);
		verify(session, times(1)).delete(auditorie);
	}
	
	@Test
	public void shouldDeleteAuditorieException(){
		doThrow(new HibernateException("Can not delete from DB")).when(session).delete(any(Auditorie.class));
		Boolean resultDelete = persistence.deleteAuditories(getAuditorie());
		assertEquals(resultDelete, false);
	}
	
	/*
	 *@Test
	  public void shouldLoadLessonsTime(){
		LessonsTime lessonsTime = getLessonsTimeClass(12);
		when(session.load(LessonsTime.class, 12)).thenReturn(lessonsTime);
		LessonsTime resultLoad = persistence.loadLessonsTime(12);
		assertEquals(resultLoad, lessonsTime);
	}
	 */
	
	@Test
	public void shouldLoadAuditorie(){
		Auditorie auditorie = getAuditorie();
		when(session.load(Auditorie.class, 1)).thenReturn(auditorie);
		Auditorie resultLoad = persistence.loadAuditorie(1);
		assertEquals(resultLoad, auditorie);
	}
	
	@Test
	public void shouldLoadAuditorieException(){
		doThrow(new HibernateException("Can not load from DB")).when(session).load(any(Auditorie.class), any(Integer.class));
		Auditorie resultLoad = persistence.loadAuditorie(1);
		assertEquals(resultLoad, null);
	}

	@Test
	public void shouldUpdateAuditorie(){
		Auditorie auditorie = getAuditorie();
		Boolean resultUpdate = persistence.updateAuditories(auditorie);
		assertEquals(resultUpdate, true);
		verify(session, times(1)).update(auditorie);
	}
	
	@Test
	public void shouldUpdateAuditorieException(){
		doThrow(new HibernateException("Can not update to DB")).when(session).update(any(Auditorie.class));
		Boolean resultUpdate = persistence.updateAuditories(getAuditorie());
		assertEquals(resultUpdate,false);
	}
	
	@Test 
	public void shouldGetListAuditorie(){
		List<Auditorie> listAuditorie = new ArrayList<Auditorie>();
		listAuditorie.add(getAuditorie());
		when(session.createCriteria(any(Class.class))).thenReturn(criteria);
		when(criteria.list()).thenReturn(listAuditorie);
		List<Auditorie> resultList = persistence.getListAuditorie();
		assertNotNull(resultList);
		assertEquals(resultList.size(), listAuditorie.size());
		System.out.println("result list: " + resultList.size());
		assertEquals(resultList.get(0).getId(), listAuditorie.get(0).getId());
		assertEquals(resultList.get(0).getAudNumber(), listAuditorie.get(0).getAudNumber());
		assertEquals(resultList.get(0).getMaxPerson(), listAuditorie.get(0).getMaxPerson());
	}
	
	@Test
	public void shouldGetListAuditorieException(){
		when(session.createCriteria(any(Class.class))).thenReturn(criteria);
		doThrow(new HibernateException("Can not get list of LessonsTime")).when(criteria).list();
		List <Auditorie> listResult = persistence.getListAuditorie();
		assertEquals(listResult, null);
	}

	private Auditorie getAuditorie() {
		Auditorie auditorie = new Auditorie();
		auditorie.setId(1);
		auditorie.setAudNumber("01A");
		auditorie.setMaxPerson(40);
		return auditorie;
	}
	
}
