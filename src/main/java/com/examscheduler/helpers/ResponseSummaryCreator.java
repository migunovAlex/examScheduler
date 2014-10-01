package com.examscheduler.helpers;

import com.examscheduler.dto.ErrorData;
import com.examscheduler.dto.summary.AbstractSummary;
import com.examscheduler.summary.OperationResultSummary;

public class ResponseSummaryCreator {
	
	public AbstractSummary generateExpiredSessionMessageResponse() {
		return generateErrorMessage(new OperationResultSummary(),  ErrorData.EXPIRED_SESSION_CODE, ErrorData.EXPIRED_SESSION_MESSAGE);
	}
	
	public AbstractSummary generateErrorConnectionToDataBaseResponse(){
		return generateErrorMessage(new OperationResultSummary(), ErrorData.ERROR_WHILE_OPERATE_WITH_DB_CODE, ErrorData.ERROR_WHILE_OPERATE_WITH_DB_MESSAGE);
	}
	
	public AbstractSummary generateNotProperDataResponse(){
		return generateErrorMessage(new OperationResultSummary(), ErrorData.NOT_PROPER_DATA, ErrorData.NOT_PROPER_DATA_MESSAGE);
	}
	
	private AbstractSummary generateErrorMessage(AbstractSummary abstractSummary, int errorCode, String errorMessage) {
		ErrorData errorData = new ErrorData();
		errorData.setNumberCode(errorCode);
		errorData.setDescription(errorMessage);
		abstractSummary.setErrorData(errorData);
		return abstractSummary;
	}

}
