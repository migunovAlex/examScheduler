package com.examscheduler.controllers.tools;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.junit.Test;

import com.examscheduler.entity.LessonsTime;

public class LessonsTimeValidatorTest {

	private LessonsTimeValidator testInstance = new LessonsTimeValidator();

	@Test
	public void shouldNotValidateWithNotRightParameter() {
		LessonsTime lessonTime = createTestLessonsTime(1, "10:00", "11:30");
		assertEquals(Boolean.FALSE, testInstance.isValid(lessonTime, Collections.<LessonsTime> emptyList()));
	}

	private LessonsTime createTestLessonsTime(int lessonsNumber, String timeStart, String timeEnd) {
		LessonsTime lessonTime = new LessonsTime();
		lessonTime.setLessonNumber(lessonsNumber);
		lessonTime.setTimeStart(timeStart);
		lessonTime.setTimeEnd(timeEnd);
		return lessonTime;
	}

}
