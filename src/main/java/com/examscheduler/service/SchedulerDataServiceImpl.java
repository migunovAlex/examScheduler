package com.examscheduler.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.examscheduler.controllers.tools.LessonsTimeValidator;
import com.examscheduler.dto.AuditoryDTO;
import com.examscheduler.dto.LessonTimeDTO;
import com.examscheduler.dto.summary.AbstractSummary;
import com.examscheduler.dto.summary.AuditoryListSummary;
import com.examscheduler.dto.summary.LessonsTimeListSummary;
import com.examscheduler.entity.Auditory;
import com.examscheduler.entity.LessonsTime;
import com.examscheduler.persistence.PersistenceDAO;
import com.examscheduler.summary.OperationResultSummary;

@Component
public class SchedulerDataServiceImpl implements SchedulerDataService {

	@Autowired
	private PersistenceDAO persistenceDao;
	@Autowired
	private LessonsTimeValidator lessonsTimeValidator;
	
	public AbstractSummary createLessonTime(LessonTimeDTO lessonTimeDTO) {
		if(lessonTimeDTO==null)
			throw new IllegalArgumentException("Lessons Time is null");
		LessonsTime lessonTime = new LessonsTime();
		lessonTime.setLessonNumber(lessonTimeDTO.getLessonNumber());
		lessonTime.setTimeStart(lessonTimeDTO.getTimeStart());
		lessonTime.setTimeEnd(lessonTimeDTO.getTimeEnd());
		OperationResultSummary operationResult = new OperationResultSummary();
		operationResult.setOperationResult(persistenceDao
				.createLessonsTime(lessonTime));
		return operationResult;
	}

	public LessonTimeDTO updateLessonTime(LessonTimeDTO lessonTimeDTO) {

		LessonsTime lessonTime = persistenceDao.loadLessonsTime(Integer.parseInt(lessonTimeDTO
				.getId()));
		lessonTime.setLessonNumber(lessonTimeDTO.getLessonNumber());
		lessonTime.setTimeStart(lessonTimeDTO.getTimeStart());
		lessonTime.setTimeEnd(lessonTimeDTO.getTimeEnd());
		Boolean updLessonTime = persistenceDao.updateLessonsTime(lessonTime);

		if (updLessonTime == true) {
			return lessonTimeDTO;
		}

		return null;
	}

	public Boolean deleteLessonTime(Integer lessonTimeId) {

		LessonsTime lessonTime = persistenceDao.loadLessonsTime(lessonTimeId);
		Boolean delLessonTime = persistenceDao.deleteLessonsTime(lessonTime);
		return delLessonTime;
	}

	public LessonsTimeListSummary getListLessonTime() {
		LessonsTimeListSummary result = new LessonsTimeListSummary();
		List<LessonsTime> listLessonTime = persistenceDao.getListLessonTime();
		List<LessonTimeDTO> listLessonTimeDTO = new ArrayList<LessonTimeDTO>();
		for (int i = 0; i < listLessonTime.size(); i++) {
			LessonTimeDTO lessonTimeDTO = new LessonTimeDTO();
			lessonTimeDTO.setId(String.valueOf(listLessonTime.get(i).getId()));
			lessonTimeDTO.setLessonNumber(listLessonTime.get(i)
					.getLessonNumber());
			lessonTimeDTO.setTimeStart(listLessonTime.get(i).getTimeStart());
			lessonTimeDTO.setTimeEnd(listLessonTime.get(i).getTimeEnd());
			listLessonTimeDTO.add(lessonTimeDTO);
		}
		result.setLessonsTimeList(listLessonTimeDTO);
		return result;
	}

	public LessonTimeDTO loadLessonTime(Integer lessonTimeId) {
		LessonsTime lessonTime = persistenceDao.loadLessonsTime(lessonTimeId);
		if (lessonTime != null) {
			LessonTimeDTO lessonTimeDTO = new LessonTimeDTO();
			lessonTimeDTO.setId(String.valueOf(lessonTime.getId()));
			lessonTimeDTO.setLessonNumber(lessonTime.getLessonNumber());
			lessonTimeDTO.setTimeStart(lessonTime.getTimeStart());
			lessonTimeDTO.setTimeEnd(lessonTime.getTimeEnd());
			return lessonTimeDTO;
		}
		return null;
	}

	public OperationResultSummary createAuditory(AuditoryDTO auditoryDTO) {
		if (auditoryDTO == null)
			throw new IllegalArgumentException("Auditory is null");
		Auditory auditory = new Auditory();
		auditory.setAudNumber(auditoryDTO.getAudNumber());
		auditory.setMaxPerson(auditoryDTO.getMaxPerson());
		boolean isAuditoryCreated = persistenceDao.createAuditories(auditory);
		OperationResultSummary result = new OperationResultSummary();
		result.setOperationResult(isAuditoryCreated);
		return result;
	}

	public OperationResultSummary updateAuditory(AuditoryDTO auditoryDTO) {
		if(auditoryDTO == null)
			throw new IllegalArgumentException("Auditory is null");
		Auditory auditory = persistenceDao.loadAuditorie(auditoryDTO.getId());
		auditory.setAudNumber(auditoryDTO.getAudNumber());
		auditory.setMaxPerson(auditory.getMaxPerson());
		boolean isAuditoryUpdate = persistenceDao.updateAuditories(auditory);
		OperationResultSummary result = new OperationResultSummary();
		result.setOperationResult(isAuditoryUpdate);
		return result;
	}

	public OperationResultSummary deleteAuditory(Integer auditoryId) {
		if (auditoryId == null)
			throw new IllegalArgumentException("Auditory Id is null");
		boolean isAuditoryDelete = persistenceDao.deleteAuditories(persistenceDao.loadAuditorie(auditoryId));
		OperationResultSummary result = new OperationResultSummary();
		result.setOperationResult(isAuditoryDelete);
		return result;
	}

	public AuditoryListSummary getListAuditory() {
		AuditoryListSummary result = new AuditoryListSummary();
		List<Auditory> auditoryList = persistenceDao.getAuditory();
		List<AuditoryDTO> auditoryDTOList = new ArrayList<AuditoryDTO>();
		for(int i=0;i<auditoryList.size();i++){
			AuditoryDTO auditoryDTO = new AuditoryDTO();
			auditoryDTO.setId(auditoryList.get(i).getId());
			auditoryDTO.setAudNumber(auditoryList.get(i).getAudNumber());
			auditoryDTO.setMaxPerson(auditoryList.get(i).getMaxPerson());
			auditoryDTOList.add(auditoryDTO);
		}
		
		result.setAuditoryList(auditoryDTOList);
		return result;
	}
	
	public void setLessonsTimeValidator(LessonsTimeValidator lessonsTimeValidator) {
		this.lessonsTimeValidator = lessonsTimeValidator;
	}

	public void setPersistenceDao(PersistenceDAO persistenceDao) {
		this.persistenceDao = persistenceDao;
	}
}
