package com.examscheduler.dto.summary;

import com.examscheduler.dto.LessonTimeDTO;

public class LessonTimeSummary extends AbstractSummary {
	
	private LessonTimeDTO lessonTime;
	
	public LessonTimeSummary(){
		super();
	}

	public LessonTimeDTO getLessonTime() {
		return lessonTime;
	}

	public void setLessonTime(LessonTimeDTO lessonTime) {
		this.lessonTime = lessonTime;
	}

}
