package com.examscheduler.summary;

import com.examscheduler.dto.ErrorData;

public class OperationResultSummary {
	
	private boolean operationResult;
	private ErrorData errorData;
	
	public OperationResultSummary(){
		errorData = new ErrorData();
	}
	
	public ErrorData getErrorData() {
		return errorData;
	}

	public void setErrorData(ErrorData errorData) {
		this.errorData = errorData;
	}
	
	public boolean isOperationResult() {
		return operationResult;
	}
	public void setOperationResult(boolean operationResult) {
		this.operationResult = operationResult;
	}
	

}
