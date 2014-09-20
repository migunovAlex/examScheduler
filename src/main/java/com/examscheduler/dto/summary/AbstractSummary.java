package com.examscheduler.dto.summary;

import com.examscheduler.dto.ErrorData;

public abstract class AbstractSummary {
	
	protected ErrorData errorData;
	
	public AbstractSummary(){
		errorData = new ErrorData();
	}
	
	public ErrorData getErrorData() {
		return errorData;
	}
	public void setErrorData(ErrorData errorData) {
		this.errorData = errorData;
	}

}
