package com.examscheduler.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.examscheduler.controllers.tools.LessonsTimeConverter;
import com.examscheduler.controllers.tools.LessonsTimeValidator;
import com.examscheduler.dto.LessonTimeDTO;
import com.examscheduler.dto.summary.LessonsTimeListSummary;
import com.examscheduler.entity.LessonsTime;
import com.examscheduler.persistence.PersistenceDAO;
import com.examscheduler.summary.OperationResultSummary;

public class SchedulerDataServiceImplTest {
	
	private static final String TIME_END = "09:30";
	private static final String TIME_START = "08:00";
	private SchedulerDataServiceImpl schedulerDataService;
	private static final Integer lessonId = 1;
	
	@Mock
	private PersistenceDAO persistenceDao;
	@Mock
	private LessonsTimeValidator lessonsTimeValidator;
	@Mock
	private LessonsTimeConverter lessonsTimeConverter;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		schedulerDataService = new SchedulerDataServiceImpl();
		schedulerDataService.setPersistenceDao(persistenceDao);
		schedulerDataService.setLessonsTimeConverter(lessonsTimeConverter);
		schedulerDataService.setLessonsTimeValidator(lessonsTimeValidator);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldCreateLessonTime(){
		LessonTimeDTO preparedLessonsTime = prepareLessonsTimeDTO();
		LessonsTime convertedLessonsTime = loadLessonsTime();
		when(lessonsTimeConverter.convertFromDTO(preparedLessonsTime)).thenReturn(convertedLessonsTime);
		when(lessonsTimeValidator.isValid(any(LessonsTime.class), any(List.class))).thenReturn(Boolean.TRUE);
		when(persistenceDao.createLessonsTime(any(LessonsTime.class))).thenReturn(true);
		OperationResultSummary lessonTimeCreate = (OperationResultSummary) schedulerDataService.createLessonTime(preparedLessonsTime);
		assertEquals(Boolean.TRUE, lessonTimeCreate.isOperationResult());
	}
	

	@Test
	public void shouldUpdateLessonsTime(){
		when(persistenceDao.updateLessonsTime(any(LessonsTime.class))).thenReturn(true);
		when(persistenceDao.loadLessonsTime(any(Integer.class))).thenReturn(loadLessonsTime());
		LessonTimeDTO result = schedulerDataService.updateLessonTime(prepareLessonsTimeDTO());
		assertNotNull(result);
		assertEquals(result.getId(), String.valueOf(loadLessonsTime().getId()));
		assertEquals(result.getLessonNumber(), loadLessonsTime().getLessonNumber());
		assertEquals(result.getTimeStart(), loadLessonsTime().getTimeStart());
		assertEquals(result.getTimeEnd(), loadLessonsTime().getTimeEnd());
	}	
	
	@Test
	public void shouldDeleteLessonTime(){
		LessonsTime getLessonsTimeById = persistenceDao.loadLessonsTime(lessonId);
		when(persistenceDao.deleteLessonsTime(getLessonsTimeById)).thenReturn(true);
		Boolean lessonTimeDelete = schedulerDataService.deleteLessonTime(lessonId);
		assertEquals(lessonTimeDelete, true);
	}
	
	@Test 
	public void shouldLoadLessonTime(){
		LessonsTime lessonTimeReturn = loadLessonsTime();
		when(persistenceDao.loadLessonsTime(lessonId)).thenReturn(lessonTimeReturn);
		LessonTimeDTO lessonTimeConvert = new LessonTimeDTO();
		lessonTimeConvert.setId(String.valueOf(loadLessonsTime().getId()));
		lessonTimeConvert.setLessonNumber(loadLessonsTime().getLessonNumber());
		lessonTimeConvert.setTimeStart(loadLessonsTime().getTimeStart());
		lessonTimeConvert.setTimeEnd(loadLessonsTime().getTimeEnd());
		
		LessonTimeDTO loadLessonTimeDTO = schedulerDataService.loadLessonTime(lessonId);
		assertNotNull(loadLessonTimeDTO);
		assertEquals(lessonTimeConvert.getId(), loadLessonTimeDTO.getId());
		assertEquals(lessonTimeConvert.getLessonNumber(), loadLessonTimeDTO.getLessonNumber());
		assertEquals(lessonTimeConvert.getTimeStart(), loadLessonTimeDTO.getTimeStart());
		assertEquals(lessonTimeConvert.getTimeEnd(), loadLessonTimeDTO.getTimeEnd());
	}
	
	@Test
	public void shouldNotLoadLessonTime(){
		when(persistenceDao.loadLessonsTime(any(Integer.class))).thenReturn(null);
		LessonTimeDTO loadLessonTimeDTO = schedulerDataService.loadLessonTime(5);
		assertNull(loadLessonTimeDTO);
	}
	
	@Test
	public void shouldGetListLessonTime(){
		List<LessonsTime> listLessonTime = new ArrayList<LessonsTime>();
		listLessonTime.add(loadLessonsTime());
		
		when(persistenceDao.getListLessonTime()).thenReturn(listLessonTime);
		LessonsTimeListSummary lessonsTimeSummary = schedulerDataService.getListLessonTime();
		List<LessonTimeDTO> listLessonTimeDTO = lessonsTimeSummary.getLessonsTimeList();
		
		assertNotNull(listLessonTimeDTO);
		assertEquals(listLessonTimeDTO.size(), listLessonTime.size());
		assertEquals(listLessonTimeDTO.get(0).getId(), String.valueOf(listLessonTime.get(0).getId()));
		assertEquals(listLessonTimeDTO.get(0).getLessonNumber(), listLessonTime.get(0).getLessonNumber());
		assertEquals(listLessonTimeDTO.get(0).getTimeStart(), listLessonTime.get(0).getTimeStart());
		assertEquals(listLessonTimeDTO.get(0).getTimeEnd(), listLessonTime.get(0).getTimeEnd());
	}
	
	@Test
	public void shouldNotUpdateLessonTime(){
		when(persistenceDao.updateLessonsTime(any(LessonsTime.class))).thenReturn(false);
		when(persistenceDao.loadLessonsTime(any(Integer.class))).thenReturn(loadLessonsTime());
		LessonTimeDTO result = schedulerDataService.updateLessonTime(prepareLessonsTimeDTO());
		assertNull(result);
	}
	
	
	private LessonTimeDTO prepareLessonsTimeDTO(){
		LessonTimeDTO lessonTimeResult = new LessonTimeDTO();
		lessonTimeResult.setId(String.valueOf(5));
		lessonTimeResult.setLessonNumber(5);
		lessonTimeResult.setTimeStart(TIME_START);
		lessonTimeResult.setTimeEnd(TIME_END);
		return lessonTimeResult;
	}
	
	private LessonsTime loadLessonsTime(){
		LessonsTime lessonTimeResult = new LessonsTime();
		lessonTimeResult.setId(5);
		lessonTimeResult.setLessonNumber(5);
		lessonTimeResult.setTimeStart(TIME_START);
		lessonTimeResult.setTimeEnd(TIME_END);
		return lessonTimeResult;
	}

}
