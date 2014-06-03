package com.examscheduler.summary;

import com.examscheduler.dto.ErrorData;
import com.examscheduler.dto.SessionDTO;

public class SessionSummary {
	
	private SessionDTO session;
	private ErrorData errorData;
	
	public SessionSummary(){
		errorData = new ErrorData();
	}

	public SessionDTO getSession() {
		return session;
	}

	public void setSession(SessionDTO session) {
		this.session = session;
	}

	public ErrorData getErrorData() {
		return errorData;
	}

	public void setErrorData(ErrorData errorData) {
		this.errorData = errorData;
	}
	
	

}
