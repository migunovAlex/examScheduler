package com.examscheduler.controllers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import com.examscheduler.controllers.tools.CookieHelper;
import com.examscheduler.dto.LessonTimeDTO;
import com.examscheduler.dto.summary.AbstractSummary;
import com.examscheduler.dto.summary.LessonTimeSummary;
import com.examscheduler.dto.summary.LessonsTimeListSummary;
import com.examscheduler.security.service.UserDetailService;
import com.examscheduler.service.SchedulerDataService;
import com.examscheduler.summary.OperationResultSummary;

public class OperationControllerTest {
	
	private static final String TIME_END = "09:30";
	private static final String TIME_START = "08:00";
	private static final String FAKE_SESSION = "FAKE_SESSION";
	private static final Integer lessonId = 10;
	
	private OperationController controller;
	private LessonTimeDTO lessonTimeDTO = new LessonTimeDTO();
	
	@Mock
	private SchedulerDataService schedulerService;
	@Mock
	private HttpServletRequest request;
	@Mock
	private CookieHelper cookieHelper;
	@Mock
	private UserDetailService userDetailService;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		controller = new OperationController();
		controller.setServiceDataScheduler(schedulerService);
		controller.setCookieHelper(cookieHelper);
		controller.setUserDetailService(userDetailService);
		when(cookieHelper.getSessionCookie(request)).thenReturn(FAKE_SESSION);
		when(userDetailService.isUserStillLoggedIn(FAKE_SESSION)).thenReturn(Boolean.TRUE);
	}

	@Test
	public void shouldCreateLessonTime(){
		when(schedulerService.createLessonTime(any(LessonTimeDTO.class))).thenReturn(generateOperationResult(Boolean.TRUE));
		OperationResultSummary operationResult = (OperationResultSummary) controller.createLessonsTime(request, prepareLessonTime());
		assertEquals(Boolean.TRUE, operationResult.isOperationResult());
	}
	
	@Test
	public void shouldNotCreateLessonTimeWherUserIsNotLoggedIn(){
		when(userDetailService.isUserStillLoggedIn(FAKE_SESSION)).thenReturn(Boolean.FALSE);
		when(schedulerService.createLessonTime(any(LessonTimeDTO.class))).thenReturn(generateOperationResult(Boolean.FALSE));
		OperationResultSummary operationResult = (OperationResultSummary) controller.createLessonsTime(request, prepareLessonTime());
		assertEquals(Boolean.FALSE, operationResult.isOperationResult());
	}
	
	@Test
	public void shouldNotCreateLessonTime(){
		when(schedulerService.createLessonTime(any(LessonTimeDTO.class))).thenReturn(generateOperationResult(Boolean.FALSE));
		OperationResultSummary operationResult = (OperationResultSummary) schedulerService.createLessonTime(prepareLessonTime());
		assertEquals(Boolean.FALSE, operationResult.isOperationResult());
	}
	
	@Test
	public void shouldUpdateLessonTime(){
		when(schedulerService.updateLessonTime(any(LessonTimeDTO.class))).thenReturn(generateOperationResult(Boolean.TRUE));
		OperationResultSummary result = (OperationResultSummary) controller.updLessonsTime(prepareLessonTime());
		assertEquals(Boolean.TRUE, result.isOperationResult());
	}

	private OperationResultSummary generateOperationResult(Boolean resultFlag) {
		OperationResultSummary operationResult = new OperationResultSummary();
		operationResult.setOperationResult(resultFlag);
		return operationResult;
	}
	
	@Test
	public void shouldUpdateLessonTimeFalse(){
		when(schedulerService.updateLessonTime(any(LessonTimeDTO.class))).thenReturn(generateOperationResult(Boolean.TRUE));
		OperationResultSummary result = (OperationResultSummary) controller.updLessonsTime(prepareLessonTime());
		assertEquals(Boolean.TRUE, result.isOperationResult());
	}
	
	@Test
	public void shouldDeleteLessonTime(){
		when(schedulerService.deleteLessonTime(lessonId)).thenReturn(generateOperationResult(Boolean.TRUE));
		OperationResultSummary result = (OperationResultSummary) controller.deleteLessonsTime(lessonId);
		assertEquals(Boolean.TRUE, result.isOperationResult());
	}
	
	@Test
	public void shouldDeleteLessonTimeFalse(){
		when(schedulerService.deleteLessonTime(lessonId)).thenReturn(generateOperationResult(Boolean.FALSE));
		OperationResultSummary result = (OperationResultSummary) controller.deleteLessonsTime(lessonId);
		assertEquals(Boolean.FALSE, result.isOperationResult());
	}
	
	@Test
	public void shouldGetListLessonTime(){
		LessonsTimeListSummary result = new LessonsTimeListSummary();
		List<LessonTimeDTO> listLessonTime = new ArrayList<LessonTimeDTO>();
		listLessonTime.add(prepareLessonTime());
		result.setLessonsTimeList(listLessonTime);
		when(schedulerService.getListLessonTime()).thenReturn(result);
		AbstractSummary listResult = controller.getListLessonTime(request);
		assertEquals(result, listResult);
	}
	
	@Test
	public void shouldNotGetLessonTimeListWherUserIsNotLoggedIn(){
		when(userDetailService.isUserStillLoggedIn(FAKE_SESSION)).thenReturn(Boolean.FALSE);
		OperationResultSummary result = new OperationResultSummary();
		when(schedulerService.createLessonTime(any(LessonTimeDTO.class))).thenReturn(result);
		LessonsTimeListSummary operationResult = (LessonsTimeListSummary) controller.getListLessonTime(request);
		assertEquals(null, operationResult.getLessonsTimeList());
	}
	
	@Test
	public void shouldGetLessonTime(){
		LessonTimeSummary result = new LessonTimeSummary();
		result.setLessonTime(lessonTimeDTO);
		when(schedulerService.loadLessonTime(lessonId)).thenReturn(result);
		LessonTimeSummary lessonTimeResult = (LessonTimeSummary) controller.getLessonTime(lessonId);
		assertEquals(lessonTimeDTO, lessonTimeResult.getLessonTime());
	}
	
	private LessonTimeDTO prepareLessonTime(){
		LessonTimeDTO lessonTimeMerge = new LessonTimeDTO();
		lessonTimeMerge.setLessonNumber(2);
		lessonTimeMerge.setTimeStart(TIME_START);
		lessonTimeMerge.setTimeEnd(TIME_END);
		return lessonTimeMerge;
	}

	
}
