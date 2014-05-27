package com.examscheduler.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.examscheduler.dto.LessonTimeDTO;
import com.examscheduler.entity.LessonsTime;
import com.examscheduler.persistence.PersistenceDAO;

@Component
public class SchedulerDataServiceImpl implements SchedulerDataService{
	
	@Autowired
	private PersistenceDAO persistenceDao;

	public PersistenceDAO getPersistenceDao() {
		return persistenceDao;
	}

	public void setPersistenceDao(PersistenceDAO persistenceDao) {
		this.persistenceDao = persistenceDao;
	}

	public Boolean createLessonTime(LessonTimeDTO lessonTimeDTO) {
		LessonsTime lessonTime = new LessonsTime();
		lessonTime.setLessonNumber(lessonTimeDTO.getLessonNumber());
		lessonTime.setTimeStart(lessonTimeDTO.getTimeStart());
		lessonTime.setTimeEnd(lessonTimeDTO.getTimeEnd());
		Boolean createLessonTime = persistenceDao.createLessonsTime(lessonTime);
		return createLessonTime;
	}

	public LessonTimeDTO updateLessonTime(LessonTimeDTO lessonTimeDTO) {
		
		LessonsTime lessonTime = persistenceDao.loadLessonsTime(lessonTimeDTO.getId());
		lessonTime.setLessonNumber(lessonTimeDTO.getLessonNumber());
		lessonTime.setTimeStart(lessonTimeDTO.getTimeStart());
		lessonTime.setTimeEnd(lessonTimeDTO.getTimeEnd());
		Boolean updLessonTime = persistenceDao.updateLessonsTime(lessonTime);
		
		if(updLessonTime==true){
			return lessonTimeDTO;
		}
		
		return null;
	}

	public Boolean deleteLessonTime(Integer lessonTimeId) {
		
		LessonsTime lessonTime = persistenceDao.loadLessonsTime(lessonTimeId);
		Boolean delLessonTime = persistenceDao.deleteLessonsTime(lessonTime);
		return delLessonTime;
	}

	public List<LessonTimeDTO> getListLessonTime() {

		List<LessonsTime> listLessonTime = persistenceDao.getListLessonTime();
		List<LessonTimeDTO> listLessonTimeDTO = new ArrayList<LessonTimeDTO>();
		for(int i=0; i<listLessonTime.size(); i++){
			LessonTimeDTO lessonTimeDTO = new LessonTimeDTO();
			lessonTimeDTO.setId(listLessonTime.get(i).getId());
			lessonTimeDTO.setLessonNumber(listLessonTime.get(i).getLessonNumber());
			lessonTimeDTO.setTimeStart(listLessonTime.get(i).getTimeStart());
			lessonTimeDTO.setTimeEnd(listLessonTime.get(i).getTimeEnd());
			listLessonTimeDTO.add(lessonTimeDTO);
		}
		return listLessonTimeDTO;
	}

	public LessonTimeDTO loadLessonTime(Integer lessonTimeId) {
		LessonsTime lessonTime = persistenceDao.loadLessonsTime(lessonTimeId);
		if(lessonTime!=null){
			LessonTimeDTO lessonTimeDTO = new LessonTimeDTO();
			lessonTimeDTO.setId(lessonTime.getId());
			lessonTimeDTO.setLessonNumber(lessonTime.getLessonNumber());
			lessonTimeDTO.setTimeStart(lessonTime.getTimeStart());
			lessonTimeDTO.setTimeEnd(lessonTime.getTimeEnd());
			return lessonTimeDTO;
		}
		return null;
	}
	
}
