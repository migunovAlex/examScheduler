package com.examscheduler.service;

import com.examscheduler.dto.LessonTimeDTO;
import com.examscheduler.dto.summary.LessonsTimeListSummary;

public interface SchedulerDataService {
	
	public Boolean createLessonTime(LessonTimeDTO lessonTimeDTO);

	public LessonTimeDTO updateLessonTime(LessonTimeDTO lessonTimeDTO);

	public Boolean deleteLessonTime(Integer lessonTimeId);

	public LessonsTimeListSummary getListLessonTime();

	public LessonTimeDTO loadLessonTime(Integer lessonTimeId);
	

}
