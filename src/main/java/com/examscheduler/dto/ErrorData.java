package com.examscheduler.dto;

public class ErrorData {
	
	private int numberCode;
	private String description;
	
	public static final int OK_CODE = 200;
	public static final String OK_MESSAGE = "OK";
	
	public static final int WRONG_PARAMETERS_IN_REQUEST_CODE = 400;
	public static final String WRONG_PARAMETERS_IN_REQUEST_MESSAGE = "Not correct parameters";
	
	public static final int NOT_AVAILABLE_RESOURCE_CODE = 404;
	public static final String NOT_AVAILABLE_RESOURCE_MESSAGE = "Resource is not available";
	
	public static final int ERROR_WHILE_OPERATE_WITH_DB_CODE = 601;
	public static final String ERROR_WHILE_OPERATE_WITH_DB_MESSAGE = "Error while operate with DB";
	
	public static final int EXPIRED_SESSION_CODE = 602;
	public static final String EXPIRED_SESSION_MESSAGE = "The session has been expired";
	
	public static final int NO_SUCH_SESSION_CODE = 603;
	public static final String NO_SUCH_SESSION_MESSAGE = "There is no such session";
	
	public static final int ERROR_WHILE_EXECUTE_OPERATION_CODE = 604;
	public static final String ERROR_WHILE_EXECUTE_OPERATION_MESSAGE = "Error while execute operation";
	
	public ErrorData(){
		this.numberCode = OK_CODE;
		this.description = OK_MESSAGE;
	}
	
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
