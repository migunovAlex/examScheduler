package com.examscheduler.security.persistence;

import com.examscheduler.exceptions.PersistentActionException;
import com.examscheduler.security.persistence.entity.UserSession;

public interface SessionDAO {
	
	public UserSession getUserSessionByValue(String userSession);
	
	public void updateUserSession(UserSession userSession);
	
	public int saveSession(UserSession userSession) throws PersistentActionException;

}
