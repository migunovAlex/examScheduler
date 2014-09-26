package com.examscheduler.controllers.tools;

import com.examscheduler.dto.LessonTimeDTO;
import com.examscheduler.entity.LessonsTime;

public class LessonsTimeConverter {

	public LessonsTime convertFromDTO(LessonTimeDTO lessonTimeDTO) {
		LessonsTime lessonTime = new LessonsTime();
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
