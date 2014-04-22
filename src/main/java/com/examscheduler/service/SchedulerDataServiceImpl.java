package com.examscheduler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.examscheduler.entity.LessonsTime;
import com.examscheduler.persistence.PersistenceDAO;

@Transactional
public class SchedulerDataServiceImpl implements SchedulerDataService{
	
	@Autowired
	private PersistenceDAO persistenceDao;

	public Boolean createLessonTime(Integer lessonNum, Integer startTime,
			Integer endTime) {
		LessonsTime lessonTime = new LessonsTime();
		lessonTime.setLessonNumber(lessonNum);
		lessonTime.setTimeStart(startTime.longValue());
		lessonTime.setTimeEnd(endTime.longValue());
		Boolean createLessonTime = persistenceDao.createLessonsTime(lessonTime);
		return createLessonTime;
	}

}
