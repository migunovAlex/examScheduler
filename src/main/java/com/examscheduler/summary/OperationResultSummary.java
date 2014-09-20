package com.examscheduler.summary;

import com.examscheduler.dto.summary.AbstractSummary;

public class OperationResultSummary extends AbstractSummary{
	
	private boolean operationResult = false;
	
	public OperationResultSummary(){
		super();
	}
	
	public boolean isOperationResult() {
		return operationResult;
	}
	
	public void setOperationResult(boolean operationResult) {
		this.operationResult = operationResult;
	}
	

}
