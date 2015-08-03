package com.examscheduler.persistence;

import java.util.List;

import com.examscheduler.entity.Auditorie;
import com.examscheduler.entity.LessonsTime;

public interface PersistenceDAO {
	
	public boolean createAuditories(Auditorie auditorie);
	 
	public boolean deleteAuditories(Auditorie auditorie);
	
	public boolean updateAuditories(Auditorie auditorie);
	
	public Auditorie loadAuditorie(Integer auditorie);
	
	public List<Auditorie> getAuditorieList();
	
	public boolean createLessonsTime(LessonsTime lessonsTime);
	
	public boolean deleteLessonsTime(LessonsTime lessonsTime);
	
	public boolean updateLessonsTime(LessonsTime lessonsTime);
	
	public LessonsTime loadLessonsTime(Integer lessonsTimeId);
	
	public List<LessonsTime> getListLessonTime();
}
