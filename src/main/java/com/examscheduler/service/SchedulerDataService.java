package com.examscheduler.service;

import java.util.List;

import com.examscheduler.dto.LessonTimeDTO;

public interface SchedulerDataService {
	
	public Boolean createLessonTime(LessonTimeDTO lessonTimeDTO);

	public LessonTimeDTO updateLessonTime(LessonTimeDTO lessonTimeDTO);

	public Boolean deleteLessonTime(Integer lessonTimeId);

	public List<LessonTimeDTO> getListLessonTime();

	public LessonTimeDTO loadLessonTime(Integer lessonTimeId);
	

}
