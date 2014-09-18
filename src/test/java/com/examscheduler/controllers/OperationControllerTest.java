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

import com.examscheduler.dto.LessonTimeDTO;
import com.examscheduler.dto.summary.LessonsTimeListSummary;
import com.examscheduler.security.service.UserDetailService;
import com.examscheduler.service.SchedulerDataService;

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
	}

	@Test
	public void shouldCreateLessonTime(){
		when(schedulerService.createLessonTime(any(LessonTimeDTO.class))).thenReturn(true);
		Boolean result = controller.createLessonsTime(prepareLessonTime());
		assertEquals(result, true);
	}
	
	@Test
	public void shouldCreateLessonTimeFalse(){
		when(schedulerService.createLessonTime(any(LessonTimeDTO.class))).thenReturn(false);
		Boolean result = controller.createLessonsTime(prepareLessonTime());
		assertEquals(result, false);
	}
	
	@Test
	public void shouldUpdateLessonTime(){
		when(schedulerService.updateLessonTime(any(LessonTimeDTO.class))).thenReturn(lessonTimeDTO);
		LessonTimeDTO result = controller.updLessonsTime(prepareLessonTime());
		assertEquals(result, lessonTimeDTO);
	}
	
	@Test
	public void shouldUpdateLessonTimeFalse(){
		when(schedulerService.updateLessonTime(any(LessonTimeDTO.class))).thenReturn(lessonTimeDTO);
		LessonTimeDTO result = controller.updLessonsTime(prepareLessonTime());
		assertEquals(result, lessonTimeDTO);
	}
	
	@Test
	public void shouldDeleteLessonTime(){
		when(schedulerService.deleteLessonTime(lessonId)).thenReturn(true);
		Boolean result = controller.deleteLessonsTime(lessonId);
		assertEquals(result, true);
	}
	
	@Test
	public void shouldDeleteLessonTimeFalse(){
		when(schedulerService.deleteLessonTime(lessonId)).thenReturn(false);
		Boolean result = controller.deleteLessonsTime(lessonId);
		assertEquals(result, false);
	}
	
	@Test
	public void shouldGetListLessonTime(){
		when(cookieHelper.getSessionCookie(request)).thenReturn(FAKE_SESSION);
		when(userDetailService.isUserStillLoggedIn(FAKE_SESSION)).thenReturn(Boolean.TRUE);
		LessonsTimeListSummary result = new LessonsTimeListSummary();
		List<LessonTimeDTO> listLessonTime = new ArrayList<LessonTimeDTO>();
		listLessonTime.add(prepareLessonTime());
		result.setLessonsTimeList(listLessonTime);
		when(schedulerService.getListLessonTime()).thenReturn(result);
		LessonsTimeListSummary listResult = controller.getListLessonTime(request);
		assertEquals(result, listResult);
	}
	@Test
	public void shouldGetLessonTime(){
		when(schedulerService.loadLessonTime(lessonId)).thenReturn(lessonTimeDTO);
		LessonTimeDTO lessonTimeResult = controller.getLessonTime(lessonId);
		assertEquals(lessonTimeResult, lessonTimeDTO);
	}
	
	private LessonTimeDTO prepareLessonTime(){
		LessonTimeDTO lessonTimeMerge = new LessonTimeDTO();
		lessonTimeMerge.setLessonNumber(2);
		lessonTimeMerge.setTimeStart(TIME_START);
		lessonTimeMerge.setTimeEnd(TIME_END);
		return lessonTimeMerge;
	}

	
}
