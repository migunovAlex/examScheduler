package com.examscheduler.security.session;

import org.springframework.beans.factory.annotation.Autowired;

import com.examscheduler.security.tools.SessionGenerator;

public class SessionComponent {
	
	@Autowired
	private SessionGenerator sessionGenerator;
	
	public String generateNewSession(){
		return sessionGenerator.generateSession();
	}

}
