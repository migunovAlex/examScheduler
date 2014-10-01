package com.examscheduler.controllers.tools;

import org.apache.log4j.Logger;

import com.examscheduler.dto.LessonTimeDTO;
import com.examscheduler.entity.LessonsTime;

public class LessonsTimeConverter {
	
	private Logger logger = Logger.getLogger(LessonsTimeConverter.class);

	public LessonsTime convertFromDTO(LessonTimeDTO lessonTimeDTO) {
		LessonsTime lessonTime = new LessonsTime();
		try {
			lessonTime.setId(Integer.valueOf(lessonTimeDTO.getId()));
		} catch (NumberFormatException ex) {
			logger.warn("String value has been sent to validator");
		}
		lessonTime.setLessonNumber(lessonTimeDTO.getLessonNumber());
		lessonTime.setTimeStart(lessonTimeDTO.getTimeStart());
		lessonTime.setTimeEnd(lessonTimeDTO.getTimeEnd());
		return lessonTime;
	}

	public LessonTimeDTO convertFromPersistence(LessonsTime lessonsTime) {
		LessonTimeDTO resultDTO = new LessonTimeDTO();
		resultDTO.setId(String.valueOf(lessonsTime.getId()));
		resultDTO.setLessonNumber(lessonsTime.getLessonNumber());
		resultDTO.setTimeStart(lessonsTime.getTimeStart());
		resultDTO.setTimeEnd(lessonsTime.getTimeEnd());
		return resultDTO;
	}

}
