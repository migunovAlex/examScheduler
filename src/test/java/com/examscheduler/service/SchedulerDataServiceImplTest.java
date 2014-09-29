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
import com.examscheduler.dto.ErrorData;
import com.examscheduler.dto.LessonTimeDTO;
import com.examscheduler.dto.summary.LessonTimeSummary;
import com.examscheduler.dto.summary.LessonsTimeListSummary;
import com.examscheduler.entity.LessonsTime;
import com.examscheduler.persistence.PersistenceDAO;
import com.examscheduler.summary.OperationResultSummary;

public class SchedulerDataServiceImplTest {
	
	private static final String TIME_END = "09:30";
	private static final String TIME_START = "08:00";
	private static final int LESSON_NUMBER = 5;
	private SchedulerDataServiceImpl schedulerDataService;
	private static final int LESSON_ID = 1;
	
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
		LessonsTime convertedLessonsTime = generateLessonsTime();
		when(lessonsTimeConverter.convertFromDTO(preparedLessonsTime)).thenReturn(convertedLessonsTime);
		when(lessonsTimeValidator.isValid(any(LessonsTime.class), any(List.class))).thenReturn(Boolean.TRUE);
		when(persistenceDao.createLessonsTime(any(LessonsTime.class))).thenReturn(true);
		OperationResultSummary lessonTimeCreate = (OperationResultSummary) schedulerDataService.createLessonTime(preparedLessonsTime);
		assertEquals(Boolean.TRUE, lessonTimeCreate.isOperationResult());
	}
	

	@Test
	public void shouldUpdateLessonsTime(){
		when(persistenceDao.updateLessonsTime(any(LessonsTime.class))).thenReturn(true);
		when(persistenceDao.loadLessonsTime(any(Integer.class))).thenReturn(generateLessonsTime());
		OperationResultSummary result = (OperationResultSummary) schedulerDataService.updateLessonTime(prepareLessonsTimeDTO());
		assertNotNull(result);
		assertEquals(Boolean.TRUE, result.isOperationResult());
	}	
	
	@Test
	public void shouldDeleteLessonTime(){
		LessonsTime getLessonsTimeById = persistenceDao.loadLessonsTime(LESSON_ID);
		when(persistenceDao.deleteLessonsTime(getLessonsTimeById)).thenReturn(true);
		OperationResultSummary result = (OperationResultSummary) schedulerDataService.deleteLessonTime(LESSON_ID);
		assertEquals(Boolean.TRUE, result.isOperationResult());
	}
	
	@Test 
	public void shouldLoadLessonTime(){
		LessonsTime lessonTime = generateLessonsTime();
		when(persistenceDao.loadLessonsTime(LESSON_ID)).thenReturn(lessonTime);
		when(lessonsTimeConverter.convertFromPersistence(lessonTime)).thenReturn(prepareLessonsTimeDTO());
		
		LessonTimeSummary loadedLessonTimeSummary = (LessonTimeSummary) schedulerDataService.loadLessonTime(LESSON_ID);
		assertNotNull(loadedLessonTimeSummary);
		assertNotNull(loadedLessonTimeSummary.getLessonTime());
		assertEquals(String.valueOf(LESSON_ID), loadedLessonTimeSummary.getLessonTime().getId());
		assertEquals(LESSON_NUMBER, loadedLessonTimeSummary.getLessonTime().getLessonNumber());
		assertEquals(TIME_START, loadedLessonTimeSummary.getLessonTime().getTimeStart());
		assertEquals(TIME_END, loadedLessonTimeSummary.getLessonTime().getTimeEnd());
	}
	
	@Test
	public void shouldNotLoadLessonTime(){
		when(persistenceDao.loadLessonsTime(any(Integer.class))).thenReturn(null);
		LessonTimeSummary loadLessonTimeDTO = (LessonTimeSummary) schedulerDataService.loadLessonTime(5);
		assertNotNull(loadLessonTimeDTO);
		assertNull(loadLessonTimeDTO.getLessonTime());
		assertEquals(ErrorData.NO_DATA_WITH_SUCH_ID, loadLessonTimeDTO.getErrorData().getNumberCode());
		assertEquals(ErrorData.NO_DATA_WITH_SUCH_ID_MESSAGE, loadLessonTimeDTO.getErrorData().getDescription());
	}
	
	@Test
	public void shouldGetListLessonTime(){
		List<LessonsTime> listLessonTime = new ArrayList<LessonsTime>();
		LessonsTime generatedLessonsTime = generateLessonsTime();
		listLessonTime.add(generatedLessonsTime);
		
		when(persistenceDao.getListLessonTime()).thenReturn(listLessonTime);
		when(lessonsTimeConverter.convertFromPersistence(generatedLessonsTime)).thenReturn(prepareLessonsTimeDTO());
		LessonsTimeListSummary lessonsTimeSummary = (LessonsTimeListSummary) schedulerDataService.getListLessonTime();
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
		when(persistenceDao.loadLessonsTime(any(Integer.class))).thenReturn(generateLessonsTime());
		OperationResultSummary result = (OperationResultSummary) schedulerDataService.updateLessonTime(prepareLessonsTimeDTO());
		assertNotNull(result);
		assertEquals(Boolean.FALSE, result.isOperationResult());
	}
	
	
	private LessonTimeDTO prepareLessonsTimeDTO(){
		LessonTimeDTO lessonTimeResult = new LessonTimeDTO();
		lessonTimeResult.setId(String.valueOf(LESSON_ID));
		lessonTimeResult.setLessonNumber(LESSON_NUMBER);
		lessonTimeResult.setTimeStart(TIME_START);
		lessonTimeResult.setTimeEnd(TIME_END);
		return lessonTimeResult;
	}
	
	private LessonsTime generateLessonsTime(){
		LessonsTime lessonTimeResult = new LessonsTime();
		lessonTimeResult.setId(LESSON_ID);
		lessonTimeResult.setLessonNumber(LESSON_NUMBER);
		lessonTimeResult.setTimeStart(TIME_START);
		lessonTimeResult.setTimeEnd(TIME_END);
		return lessonTimeResult;
	}

}
