package com.examscheduler.helpers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.examscheduler.dto.ErrorData;
import com.examscheduler.dto.summary.AbstractSummary;

public class ResponseSummaryCreatorTest {
	
	private ResponseSummaryCreator testInstance = new ResponseSummaryCreator();
	
	@Test
	public void shouldGenerateExpiredSessionMessage(){
		AbstractSummary generatedResponse = testInstance.generateExpiredSessionMessageResponse();
		assertEquals(ErrorData.EXPIRED_SESSION_CODE, generatedResponse.getErrorData().getNumberCode());
		assertEquals(ErrorData.EXPIRED_SESSION_MESSAGE, generatedResponse.getErrorData().getDescription());
	}
	
	@Test
	public void shouldGenerateErrorConnectionDatabaseMessage(){
		AbstractSummary generatedResponse = testInstance.generateErrorConnectionToDataBaseResponse();
		assertEquals(ErrorData.ERROR_WHILE_OPERATE_WITH_DB_CODE, generatedResponse.getErrorData().getNumberCode());
		assertEquals(ErrorData.ERROR_WHILE_OPERATE_WITH_DB_MESSAGE, generatedResponse.getErrorData().getDescription());
	}
	
	@Test
	public void shouldGenerateNotProperDataMessage(){
		AbstractSummary generatedResponse = testInstance.generateNotProperDataResponse();
		assertEquals(ErrorData.NOT_PROPER_DATA, generatedResponse.getErrorData().getNumberCode());
		assertEquals(ErrorData.NOT_PROPER_DATA_MESSAGE, generatedResponse.getErrorData().getDescription());
	}

}
