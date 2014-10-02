package com.examscheduler.controllers.tools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.examscheduler.dto.LessonTimeDTO;
import com.examscheduler.entity.LessonsTime;

public class LessonsTimeConverterTest {
	
	private static final String STRING_ID = "TEST_ID";
	private static final String TIME_END = "10:00";
	private static final String TIME_START = "08:30";
	private static final int LESSON_NUMBER = 1;
	private static final int DTO_ID = 1;
	private LessonsTimeConverter testInstance = new LessonsTimeConverter();
	
	@Test
	public void shouldConvertFromDTO(){
		LessonsTime convertFromDTO = testInstance.convertFromDTO(generateDTO());
		assertNotNull(convertFromDTO);
		assertEquals(DTO_ID, convertFromDTO.getId());
		assertEquals(LESSON_NUMBER, convertFromDTO.getLessonNumber());
		assertEquals(TIME_START, convertFromDTO.getTimeStart());
		assertEquals(TIME_END, convertFromDTO.getTimeEnd());
	}
	
	@Test
	public void shouldConvertFromEntity(){
		LessonTimeDTO convertFromPersistence = testInstance.convertFromPersistence(generatePersistanceEntity());
		assertNotNull(convertFromPersistence);
		assertEquals(String.valueOf(DTO_ID), convertFromPersistence.getId());
		assertEquals(LESSON_NUMBER, convertFromPersistence.getLessonNumber());
		assertEquals(TIME_START, convertFromPersistence.getTimeStart());
		assertEquals(TIME_END, convertFromPersistence.getTimeEnd());
	}
	
	@Test
	public void shouldNotSetIdWithStringValue(){
		LessonTimeDTO generatedDTO = generateDTO();
		generatedDTO.setId(STRING_ID);
		LessonsTime convertFromDTO = testInstance.convertFromDTO(generatedDTO);
		assertEquals(0, convertFromDTO.getId());
		assertEquals(LESSON_NUMBER, convertFromDTO.getLessonNumber());
		assertEquals(TIME_START, convertFromDTO.getTimeStart());
		assertEquals(TIME_END, convertFromDTO.getTimeEnd());
	}
	
	private LessonsTime generatePersistanceEntity() {
		LessonsTime result = new LessonsTime();
		result.setId(DTO_ID);
		result.setLessonNumber(LESSON_NUMBER);
		result.setTimeStart(TIME_START);
		result.setTimeEnd(TIME_END);
		return result;
	}

	private LessonTimeDTO generateDTO() {
		LessonTimeDTO result = new LessonTimeDTO();
		result.setId(String.valueOf(DTO_ID));
		result.setLessonNumber(LESSON_NUMBER);
		result.setTimeStart(TIME_START);
		result.setTimeEnd(TIME_END);
		return result;
	}

}
