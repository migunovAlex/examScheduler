package com.examscheduler.security.session;

import com.examscheduler.dto.SessionDTO;
import com.examscheduler.security.persistence.entity.DbUser;
import com.examscheduler.summary.SessionSummary;

public interface SessionService {
	
	public SessionSummary getNewSession(DbUser user);
	
	public boolean updateSessionActivity(SessionDTO session);
	
	public SessionSummary getCurrentSession(SessionDTO session);

}
