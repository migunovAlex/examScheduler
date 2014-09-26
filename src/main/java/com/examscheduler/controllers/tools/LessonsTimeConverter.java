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

}
