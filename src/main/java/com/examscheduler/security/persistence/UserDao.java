package com.examscheduler.security.persistence;

import com.examscheduler.security.persistence.entity.DbUser;

public interface UserDao {
	
	public DbUser searchDatabase(String username);

}
