package com.examscheduler.security.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.examscheduler.security.tools.SessionGenerator;

@Component
public class SessionComponent {
	
	@Autowired
	private SessionGenerator sessionGenerator;
	
	public String generateNewSession(){
		return sessionGenerator.generateSession();
	}

	public void setSessionGenerator(SessionGenerator sessionGenerator) {
		this.sessionGenerator = sessionGenerator;
	}


}
