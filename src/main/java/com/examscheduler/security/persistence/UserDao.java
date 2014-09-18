package com.examscheduler.security.persistence;

import com.examscheduler.security.persistence.entity.DbUser;
import com.examscheduler.security.persistence.entity.UserSession;

public interface UserDao {
	
	public DbUser searchDatabase(String username);
	
	public DbUser getUserBySessionValue(String sessionValue);

	public UserSession getUserSession(String sessionValue);

}
