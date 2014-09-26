package com.examscheduler.persistence;

import java.util.List;

import com.examscheduler.entity.Auditory;
import com.examscheduler.entity.LessonsTime;

public interface PersistenceDAO {
	
	public boolean createAuditories(Auditory auditorie);
	 
	public boolean deleteAuditories(Auditory auditorie);
	
	public boolean updateAuditories(Auditory auditorie);
	
	public Auditory loadAuditorie(Integer auditorie);
	
	public boolean createLessonsTime(LessonsTime lessonsTime);
	
	public boolean deleteLessonsTime(LessonsTime lessonsTime);
	
	public boolean updateLessonsTime(LessonsTime lessonsTime);
	
	public LessonsTime loadLessonsTime(Integer lessonsTimeId);
	
	public List<LessonsTime> getListLessonTime();

	public List<Auditory> getListAuditory();
}
