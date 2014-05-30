package com.examscheduler.security.service;

import com.examscheduler.dto.SessionDTO;
import com.examscheduler.security.persistence.entity.DbUser;
import com.examscheduler.summary.SessionSummary;

public interface SessionService {
	
	public SessionSummary getNewSession(DbUser user);
	
	public SessionSummary updateSessionActivity(SessionDTO session);
	
	public SessionSummary getCurrentSession(SessionDTO session);

}
