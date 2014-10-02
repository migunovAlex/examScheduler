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
		LessonsTime lessonTime = createTestLessonsTime(1, "10:00", "sdf:30", null);
		assertEquals(Boolean.FALSE, testInstance.isValid(lessonTime, Collections.<LessonsTime> emptyList()));
	}
	
	@Test
	public void shouldNotValidateWithNotRightStartParameter() {
		LessonsTime lessonTime = createTestLessonsTime(1, "sdf:f0", "10:30", null);
		assertEquals(Boolean.FALSE, testInstance.isValid(lessonTime, Collections.<LessonsTime> emptyList()));
	}
	
	@Test
	public void shouldNotValidateCauseSameNumber(){
		LessonsTime lessonTime = createTestLessonsTime(1, "09:40", "10:10", null);
		assertEquals(Boolean.FALSE, testInstance.isValid(lessonTime, generateLessonsTimeList()));
	}
	
	@Test
	public void shouldNotValidateCauseStartTimeConflicts(){
		LessonsTime lessonTime = createTestLessonsTime(2, "11:40", "12:00", null);
		assertEquals(Boolean.FALSE, testInstance.isValid(lessonTime, generateLessonsTimeList()));
	}
	
	@Test
	public void shouldNotValidateCauseEndTimeConflicts(){
		LessonsTime lessonTime = createTestLessonsTime(2, "09:50", "11:20", null);
		assertEquals(Boolean.FALSE, testInstance.isValid(lessonTime, generateLessonsTimeList()));
	}
	
	@Test
	public void shouldNotValidateCauseNotProperPeriod(){
		LessonsTime lessonTime = createTestLessonsTime(1, "12:50", "11:20", null);
		assertEquals(Boolean.FALSE, testInstance.isValid(lessonTime, generateLessonsTimeList()));
	}
	
	@Test
	public void shouldValidateForEditEntity(){
		LessonsTime lessonTime = createTestLessonsTime(1, "08:10", "09:20", 1);
		assertEquals(Boolean.TRUE, testInstance.isValid(lessonTime, generateLessonsTimeList()));
	}
	
	@Test
	public void shouldValidateSucessfully(){
		LessonsTime lessonTime = createTestLessonsTime(2, "09:40", "11:10", null);
		assertEquals(Boolean.TRUE, testInstance.isValid(lessonTime, generateLessonsTimeList()));
	}
	
	private List<LessonsTime> generateLessonsTimeList(){
		List<LessonsTime> resultList = new ArrayList<LessonsTime>();
		resultList.add(createTestLessonsTime(1, "08:00", "09:30", 1));
		resultList.add(createTestLessonsTime(3, "11:20", "12:50", 2));
		return resultList;
	}

	private LessonsTime createTestLessonsTime(int lessonsNumber, String timeStart, String timeEnd, Integer id) {
		LessonsTime lessonTime = new LessonsTime();
		if(id!=null)
			lessonTime.setId(id);
		lessonTime.setLessonNumber(lessonsNumber);
		lessonTime.setTimeStart(timeStart);
		lessonTime.setTimeEnd(timeEnd);
		return lessonTime;
	}

}
