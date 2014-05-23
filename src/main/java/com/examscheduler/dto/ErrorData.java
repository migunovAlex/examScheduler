package com.examscheduler.dto;

public class ErrorData {
	
	private int numberCode;
	private String description;
	
	public static final String OK_CODE = "200 - OK";
	public static final String WRONG_AVAILABLE_RESOURCE_CODE = "404 - Resource is not available";
	public static final String WRONG_PARAMETERS_IN_REQUEST_CODE = "400 - Not correct parameters";
	
	public static final int WRONG_PARAMETERS_IN_REQUEST_CODE = 400;
	public static final String WRONG_PARAMETERS_IN_REQUEST_MESSAGE = "Not correct parameters";
	
	public static final int NOT_AVAILABLE_RESOURCE_CODE = 404;
	public static final String NOT_AVAILABLE_RESOURCE_MESSAGE = "Resource is not available";
	
	public static final int ERROR_WHILE_OPERATE_WITH_DB_CODE = 601;
	public static final String ERROR_WHILE_OPERATE_WITH_DB_MESSAGE = "Error while operate with DB";
	
	public int getNumberCode() {
		return numberCode;
	}
	public void setNumberCode(int numberCode) {
		this.numberCode = numberCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
