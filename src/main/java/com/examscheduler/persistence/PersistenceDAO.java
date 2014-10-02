package com.examscheduler.persistence;

import java.util.List;

import com.examscheduler.entity.Auditory;
import com.examscheduler.entity.LessonsTime;

public interface PersistenceDAO {
	
	public boolean createAuditory(Auditory auditorie);
	 
	public boolean deleteAuditory(Auditory auditorie);
	
	public boolean updateAuditory(Auditory auditorie);
	
	public Auditory loadAuditory(Integer auditorie);
	
	public boolean createLessonsTime(LessonsTime lessonsTime);
	
	public boolean deleteLessonsTime(LessonsTime lessonsTime);
	
	public boolean updateLessonsTime(LessonsTime lessonsTime);
	
	public LessonsTime loadLessonsTime(Integer lessonsTimeId);
	
	public List<LessonsTime> getListLessonTime();

	public List<Auditory> getListAuditory();
}
