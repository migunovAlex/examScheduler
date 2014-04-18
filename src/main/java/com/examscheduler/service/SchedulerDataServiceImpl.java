package com.examscheduler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.examsscheduler.persistence.PersistenceDAO;

@Transactional
public class SchedulerDataServiceImpl implements SchedulerDataService{
	
	@Autowired
	private PersistenceDAO persistenceDao;

}
