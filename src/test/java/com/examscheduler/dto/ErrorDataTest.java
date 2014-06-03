package com.examscheduler.dto;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ErrorDataTest {
	
	private ErrorData testInstance;
	
	@Before
	public void setUp(){
		testInstance = new ErrorData();
	}
	
	@Test
	public void shouldCreateErrorDataWithOKParams(){
		assertEquals(testInstance.getNumberCode(), ErrorData.OK_CODE);
		assertEquals(testInstance.getDescription(), ErrorData.OK_MESSAGE);
	}

}
