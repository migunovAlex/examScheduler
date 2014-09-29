package com.examscheduler.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.examscheduler.controllers.tools.LessonsTimeConverter;
import com.examscheduler.controllers.tools.LessonsTimeValidator;
import com.examscheduler.dto.AuditoryDTO;
import com.examscheduler.dto.ErrorData;
import com.examscheduler.dto.LessonTimeDTO;
import com.examscheduler.dto.summary.AbstractSummary;
import com.examscheduler.dto.summary.AuditoryListSummary;
import com.examscheduler.dto.summary.LessonTimeSummary;
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
	@Autowired
	private LessonsTimeConverter lessonsTimeConverter;

	private static Logger logger = Logger.getLogger(SchedulerDataServiceImpl.class);

	public AbstractSummary createLessonTime(LessonTimeDTO lessonTimeDTO) {
		if (lessonTimeDTO == null)
			throw new IllegalArgumentException("Lessons Time is null");
		LessonsTime lessonTime = lessonsTimeConverter.convertFromDTO(lessonTimeDTO);
		OperationResultSummary result = new OperationResultSummary();
		if (!lessonsTimeValidator.isValid(lessonTime, persistenceDao.getListLessonTime())) {
			result.getErrorData().setNumberCode(ErrorData.NOT_PROPER_DATA);
			result.getErrorData().setDescription(ErrorData.NOT_PROPER_DATA_MESSAGE);
			return result;
		}
		try{
			result.setOperationResult(persistenceDao.createLessonsTime(lessonTime));
		}
		catch(HibernateException e){
			logger.error("error while creating lessonsTime - " + lessonTimeDTO.getId(), e);
			result.getErrorData().setNumberCode(ErrorData.ERROR_WHILE_OPERATE_WITH_DB_CODE);
			result.getErrorData().setDescription(ErrorData.ERROR_WHILE_OPERATE_WITH_DB_MESSAGE);
			return result;
		}
		
		return result;
	}

	public AbstractSummary updateLessonTime(LessonTimeDTO lessonTimeDTO) {
		OperationResultSummary result = new OperationResultSummary();
		boolean updLessonTime = false;
		try {
			LessonsTime lessonTime = persistenceDao.loadLessonsTime(Integer.parseInt(lessonTimeDTO.getId()));
			lessonTime.setLessonNumber(lessonTimeDTO.getLessonNumber());
			lessonTime.setTimeStart(lessonTimeDTO.getTimeStart());
			lessonTime.setTimeEnd(lessonTimeDTO.getTimeEnd());
			updLessonTime = persistenceDao.updateLessonsTime(lessonTime);
		} catch (HibernateException e) {
			logger.error("error while updating lessonsTime - " + lessonTimeDTO.getId(), e);
			result.getErrorData().setNumberCode(ErrorData.ERROR_WHILE_OPERATE_WITH_DB_CODE);
			result.getErrorData().setDescription(ErrorData.ERROR_WHILE_OPERATE_WITH_DB_MESSAGE);
			return result;
		}
		result.setOperationResult(updLessonTime);
		return result;
	}

	public AbstractSummary deleteLessonTime(int lessonTimeId) {
		OperationResultSummary result = new OperationResultSummary();
		boolean deleteLessonsTime = false;
		try{
		LessonsTime lessonTime = persistenceDao.loadLessonsTime(lessonTimeId);
		deleteLessonsTime = persistenceDao.deleteLessonsTime(lessonTime);
		}catch(HibernateException e){
			logger.error("error while deleting lessonsTime - " + lessonTimeId, e);
			result.getErrorData().setNumberCode(ErrorData.ERROR_WHILE_OPERATE_WITH_DB_CODE);
			result.getErrorData().setDescription(ErrorData.ERROR_WHILE_OPERATE_WITH_DB_MESSAGE);
			return result;
		}
		result.setOperationResult(deleteLessonsTime);
		return result;
	}

	public AbstractSummary getListLessonTime() {
		LessonsTimeListSummary result = new LessonsTimeListSummary();
		List<LessonsTime> listLessonTime = null;
		try{
			listLessonTime = persistenceDao.getListLessonTime();
		}catch(HibernateException e){
			logger.error("error while getting lessonsTime list", e);
			result.getErrorData().setNumberCode(ErrorData.ERROR_WHILE_OPERATE_WITH_DB_CODE);
			result.getErrorData().setDescription(ErrorData.ERROR_WHILE_OPERATE_WITH_DB_MESSAGE);
			return result;
		}
		
		if(listLessonTime == null) 
			listLessonTime = Collections.emptyList();
		
		List<LessonTimeDTO> listLessonTimeDTO = new ArrayList<LessonTimeDTO>();
		for (LessonsTime lessonsTime : listLessonTime) {
			listLessonTimeDTO.add(lessonsTimeConverter.convertFromPersistence(lessonsTime));
		}
		result.setLessonsTimeList(listLessonTimeDTO);
		return result;
	}

	public AbstractSummary loadLessonTime(int lessonTimeId) {
		LessonTimeSummary result = new LessonTimeSummary();
		LessonsTime lessonTime = null;
		try{
			lessonTime = persistenceDao.loadLessonsTime(lessonTimeId);
		}catch(HibernateException e){
			logger.error("error while deleting lessonsTime - " + lessonTimeId, e);
			result.getErrorData().setNumberCode(ErrorData.ERROR_WHILE_OPERATE_WITH_DB_CODE);
			result.getErrorData().setDescription(ErrorData.ERROR_WHILE_OPERATE_WITH_DB_MESSAGE);
			return result;
		}
		if (lessonTime == null) {
			result.getErrorData().setNumberCode(ErrorData.NO_DATA_WITH_SUCH_ID);
			result.getErrorData().setDescription(ErrorData.NO_DATA_WITH_SUCH_ID_MESSAGE);
			return result;
		}
		result.setLessonTime(lessonsTimeConverter.convertFromPersistence(lessonTime));
		return result;
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
		if (auditoryDTO == null)
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
		for (int i = 0; i < auditoryList.size(); i++) {
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

	public void setLessonsTimeConverter(LessonsTimeConverter lessonsTimeConverter) {
		this.lessonsTimeConverter = lessonsTimeConverter;
	}

}
