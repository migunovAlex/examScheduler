package com.examscheduler.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.examscheduler.controllers.tools.AuditoryConverter;
import com.examscheduler.controllers.tools.LessonsTimeConverter;
import com.examscheduler.controllers.tools.LessonsTimeValidator;
import com.examscheduler.dto.AuditoryDTO;
import com.examscheduler.dto.ErrorData;
import com.examscheduler.dto.LessonTimeDTO;
import com.examscheduler.dto.summary.AbstractSummary;
import com.examscheduler.dto.summary.LessonTimeSummary;
import com.examscheduler.dto.summary.LessonsTimeListSummary;
import com.examscheduler.entity.Auditory;
import com.examscheduler.entity.LessonsTime;
import com.examscheduler.helpers.ResponseSummaryCreator;
import com.examscheduler.persistence.PersistenceDAO;
import com.examscheduler.summary.OperationResultSummary;

public class SchedulerDataServiceImplTest {
	
	private static final String TIME_END = "09:30";
	private static final String TIME_START = "08:00";
	private static final int LESSON_NUMBER = 5;
	private SchedulerDataServiceImpl schedulerDataService;
	private static final int LESSON_ID = 1;
	private static final int AUDITORY_ID = 1;
	private static final String AUDITORY_NUMBER = "01B";
	private static final int AUDITORY_MAX_PERSON = 45;
	
	@Mock
	private PersistenceDAO persistenceDao;
	@Mock
	private LessonsTimeValidator lessonsTimeValidator;
	@Mock
	private LessonsTimeConverter lessonsTimeConverter;
	@Mock
	private ResponseSummaryCreator responseSummaryCreator;
	@Mock
	private AuditoryConverter auditoryConverter;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		schedulerDataService = new SchedulerDataServiceImpl();
		schedulerDataService.setPersistenceDao(persistenceDao);
		schedulerDataService.setLessonsTimeConverter(lessonsTimeConverter);
		schedulerDataService.setLessonsTimeValidator(lessonsTimeValidator);
		schedulerDataService.setResponseSummaryCreator(responseSummaryCreator);
		when(responseSummaryCreator.generateErrorConnectionToDataBaseResponse()).thenReturn(generateErrorConnectionToDBMessage());
		when(responseSummaryCreator.generateNotProperDataResponse()).thenReturn(generateNotProperDataDBMessage());
	}
	
	private AbstractSummary generateNotProperDataDBMessage() {
		OperationResultSummary result = new OperationResultSummary();
		result.getErrorData().setNumberCode(ErrorData.NOT_PROPER_DATA);
		result.getErrorData().setDescription(ErrorData.NOT_PROPER_DATA_MESSAGE);
		return result;
	}

	private AbstractSummary generateErrorConnectionToDBMessage() {
		OperationResultSummary result = new OperationResultSummary();
		result.getErrorData().setNumberCode(ErrorData.ERROR_WHILE_OPERATE_WITH_DB_CODE);
		result.getErrorData().setDescription(ErrorData.ERROR_WHILE_OPERATE_WITH_DB_MESSAGE);
		return result;
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
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldNotUpdateWithNotValidPeriod(){
		when(persistenceDao.updateLessonsTime(any(LessonsTime.class))).thenReturn(true);
		when(persistenceDao.loadLessonsTime(any(Integer.class))).thenReturn(generateLessonsTime());
		when(lessonsTimeValidator.isValid(any(LessonsTime.class), any(List.class))).thenReturn(Boolean.FALSE);
		OperationResultSummary result = (OperationResultSummary) schedulerDataService.updateLessonTime(prepareLessonsTimeDTO());
		assertNotNull(result);
		assertEquals(Boolean.FALSE, result.isOperationResult());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldUpdateLessonsTime(){
		when(persistenceDao.updateLessonsTime(any(LessonsTime.class))).thenReturn(true);
		when(persistenceDao.loadLessonsTime(any(Integer.class))).thenReturn(generateLessonsTime());
		when(lessonsTimeValidator.isValid(any(LessonsTime.class), any(List.class))).thenReturn(Boolean.TRUE);
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
		assertEquals(1, listLessonTime.size());
		assertEquals(String.valueOf(LESSON_ID), listLessonTimeDTO.get(0).getId());
		assertEquals(LESSON_NUMBER, listLessonTimeDTO.get(0).getLessonNumber());
		assertEquals(TIME_START, listLessonTimeDTO.get(0).getTimeStart());
		assertEquals(TIME_END, listLessonTimeDTO.get(0).getTimeEnd());
	}
	
	@Test
	public void shouldNotUpdateLessonTime(){
		when(persistenceDao.updateLessonsTime(any(LessonsTime.class))).thenReturn(false);
		when(persistenceDao.loadLessonsTime(any(Integer.class))).thenReturn(generateLessonsTime());
		OperationResultSummary result = (OperationResultSummary) schedulerDataService.updateLessonTime(prepareLessonsTimeDTO());
		assertNotNull(result);
		assertEquals(Boolean.FALSE, result.isOperationResult());
	}
	
	@Test
	public void shouldCreateAuditory(){
		when(persistenceDao.createAuditory(any(Auditory.class))).thenReturn(true);
		OperationResultSummary auditoryCreate = (OperationResultSummary) schedulerDataService.createAuditory(prepareAuditoryDTO());
		assertEquals(Boolean.TRUE, auditoryCreate.isOperationResult());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldCreateAuditoryException(){
		when(persistenceDao.createAuditory(any(Auditory.class))).thenThrow(new IllegalArgumentException());
		schedulerDataService.createAuditory(null);
	}
	
	@Test
	public void shouldUpdateAuditory(){
		when(persistenceDao.updateAuditory(any(Auditory.class))).thenReturn(true);
		when(persistenceDao.loadAuditory(any(Integer.class))).thenReturn(generateAuditory());
		OperationResultSummary resultUpdateAuditory = (OperationResultSummary) schedulerDataService.updateAuditory(prepareAuditoryDTO());
		assertNotNull(resultUpdateAuditory);
		assertEquals(Boolean.TRUE, resultUpdateAuditory.isOperationResult());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldUpdateAuditoryException(){
		Auditory auditory = null;
		when(persistenceDao.updateAuditory(any(Auditory.class))).thenThrow(new IllegalArgumentException());
		schedulerDataService.updateAuditory(null);
	}
	
	@Test
	public void shouldDeleteAuditory(){
		Auditory auditory = persistenceDao.loadAuditory(AUDITORY_ID);
		when(persistenceDao.deleteAuditory(auditory)).thenReturn(true);
		OperationResultSummary resultDeleteAuditory = schedulerDataService.deleteAuditory(AUDITORY_ID);
		assertEquals(Boolean.TRUE, resultDeleteAuditory.isOperationResult());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldDeleteAuditoryException(){
		when(persistenceDao.deleteAuditory(null)).thenThrow(new IllegalArgumentException());
		schedulerDataService.deleteAuditory(null);
	}
	
	@Test
	public void shouldGetListAuditory(){
		List<Auditory> listAuditory = new ArrayList<Auditory>();
		Auditory auditory = generateAuditory();
		listAuditory.add(auditory);
		when(persistenceDao.getListAuditory()).thenReturn(listAuditory);
		when(auditoryConverter.convertedFromPersistence(auditory)).thenReturn(prepareAuditoryDTO());
		List<AuditoryDTO> resultListAuditory = schedulerDataService.getListAuditory().getAuditoryList();
		
		assertNotNull(resultListAuditory);
		assertEquals(1, resultListAuditory.size());
		assertEquals(AUDITORY_ID, resultListAuditory.get(0).getId());
		assertEquals(AUDITORY_NUMBER, resultListAuditory.get(0).getAudNumber());
		assertEquals(AUDITORY_MAX_PERSON, resultListAuditory.get(0).getMaxPerson());
	}
	
	private List<Auditory> generateAuditoryList() {
		Auditory auditory = generateAuditory();
		List<Auditory> auditoryList = new ArrayList<Auditory>();
		auditoryList.add(auditory);
		return auditoryList;
	}

	private AuditoryDTO prepareAuditoryDTO() {
		AuditoryDTO auditoryDTO = new AuditoryDTO();
		auditoryDTO.setId(AUDITORY_ID);
		auditoryDTO.setAudNumber(AUDITORY_NUMBER);
		auditoryDTO.setMaxPerson(AUDITORY_MAX_PERSON);
		return auditoryDTO;
	}

	private Auditory generateAuditory() {
		Auditory auditory = new Auditory();
		auditory.setId(AUDITORY_ID);
		auditory.setAudNumber(AUDITORY_NUMBER);
		auditory.setMaxPerson(AUDITORY_MAX_PERSON);
		return auditory;
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
