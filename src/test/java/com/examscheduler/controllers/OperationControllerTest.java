package com.examscheduler.controllers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import com.examscheduler.dto.LessonTimeDTO;
import com.examscheduler.service.SchedulerDataService;

public class OperationControllerTest {
	
	private OperationController controller;
	private static final Integer lessonId = 10;
	private LessonTimeDTO lessonTimeDTO = new LessonTimeDTO();
	
	@Mock
	private SchedulerDataService schedulerService;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		controller = new OperationController();
		controller.setServiceDataScheduler(schedulerService);
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
		List<LessonTimeDTO> listLessonTime = new ArrayList<LessonTimeDTO>();
		listLessonTime.add(prepareLessonTime());
		when(schedulerService.getListLessonTime()).thenReturn(listLessonTime);
		List<LessonTimeDTO> listResult = controller.getListLessonTime();
		assertEquals(listResult, listLessonTime);
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
		lessonTimeMerge.setTimeStart(1234567890L);
		lessonTimeMerge.setTimeEnd(2345678910L);
		return lessonTimeMerge;
	}

	
}
