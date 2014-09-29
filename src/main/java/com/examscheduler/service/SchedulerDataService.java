package com.examscheduler.service;

import com.examscheduler.dto.AuditoryDTO;
import com.examscheduler.dto.LessonTimeDTO;
import com.examscheduler.dto.summary.AbstractSummary;
import com.examscheduler.dto.summary.AuditoryListSummary;
import com.examscheduler.summary.OperationResultSummary;

public interface SchedulerDataService {
	
	public AbstractSummary createLessonTime(LessonTimeDTO lessonTimeDTO);

	public AbstractSummary updateLessonTime(LessonTimeDTO lessonTimeDTO);

	public AbstractSummary deleteLessonTime(int lessonTimeId);

	public AbstractSummary getListLessonTime();

	public AbstractSummary loadLessonTime(int lessonTimeId);
	
	public OperationResultSummary createAuditory(AuditoryDTO auditory);
	
	public OperationResultSummary updateAuditory(AuditoryDTO auditoryDTO);
	
	public OperationResultSummary deleteAuditory(Integer auditoryId);

	public AuditoryListSummary getListAuditory();
	
}
