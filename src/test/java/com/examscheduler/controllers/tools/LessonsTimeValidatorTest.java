package com.examscheduler.controllers.tools;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.examscheduler.entity.LessonsTime;

public class LessonsTimeValidatorTest {

	private LessonsTimeValidator testInstance = new LessonsTimeValidator();

	@Test
	public void shouldNotValidateWithNotRightEndParameter() {
		LessonsTime lessonTime = createTestLessonsTime(1, "10:00", "sdf:30");
		assertEquals(Boolean.FALSE, testInstance.isValid(lessonTime, Collections.<LessonsTime> emptyList()));
	}
	
	@Test
	public void shouldNotValidateWithNotRightStartParameter() {
		LessonsTime lessonTime = createTestLessonsTime(1, "sdf:f0", "10:30");
		assertEquals(Boolean.FALSE, testInstance.isValid(lessonTime, Collections.<LessonsTime> emptyList()));
	}
	
	@Test
	public void shouldNotAddLessonsTimeCauseSameNumber(){
		LessonsTime lessonTime = createTestLessonsTime(1, "09:40", "10:10");
		assertEquals(Boolean.FALSE, testInstance.isValid(lessonTime, generateLessonsTimeList()));
	}
	
	@Test
	public void shouldNotAddLessonsTimeCauseStartTimeConflicts(){
		LessonsTime lessonTime = createTestLessonsTime(2, "11:40", "12:00");
		assertEquals(Boolean.FALSE, testInstance.isValid(lessonTime, generateLessonsTimeList()));
	}
	
	@Test
	public void shouldNotAddLessonsTimeCauseEndTimeConflicts(){
		LessonsTime lessonTime = createTestLessonsTime(2, "09:50", "11:20");
		assertEquals(Boolean.FALSE, testInstance.isValid(lessonTime, generateLessonsTimeList()));
	}
	
	@Test
	public void shouldNotAddLessonsTimeCauseNotProperPeriod(){
		LessonsTime lessonTime = createTestLessonsTime(1, "12:50", "11:20");
		assertEquals(Boolean.FALSE, testInstance.isValid(lessonTime, generateLessonsTimeList()));
	}
	
	@Test
	public void shouldAddLessonsTimeSucessfully(){
		LessonsTime lessonTime = createTestLessonsTime(2, "09:40", "11:10");
		assertEquals(Boolean.TRUE, testInstance.isValid(lessonTime, generateLessonsTimeList()));
	}
	
	private List<LessonsTime> generateLessonsTimeList(){
		List<LessonsTime> resultList = new ArrayList<LessonsTime>();
		resultList.add(createTestLessonsTime(1, "08:00", "09:30"));
		resultList.add(createTestLessonsTime(3, "11:20", "12:50"));
		return resultList;
	}

	private LessonsTime createTestLessonsTime(int lessonsNumber, String timeStart, String timeEnd) {
		LessonsTime lessonTime = new LessonsTime();
		lessonTime.setLessonNumber(lessonsNumber);
		lessonTime.setTimeStart(timeStart);
		lessonTime.setTimeEnd(timeEnd);
		return lessonTime;
	}

}
