package com.examscheduler.security.service;

import com.examscheduler.dto.SessionDTO;
import com.examscheduler.security.persistence.entity.DbUser;
import com.examscheduler.summary.OperationResultSummary;
import com.examscheduler.summary.SessionSummary;

public interface SessionService {
	
	public SessionSummary getNewSession(DbUser user);
	
	public OperationResultSummary updateSessionActivity(SessionDTO session);
	
	public SessionSummary getCurrentSession(SessionDTO session);
	
	public OperationResultSummary logout(String session);

}
