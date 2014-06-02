package com.examscheduler.dto;

public class ErrorData {
	
	private int numberCode;
	private String description;
	
	public static final String OK_CODE = "200 - OK";
	public static final String WRONG_AVAILABLE_RESOURCE_CODE = "404 - Resource is not available";
	public static final String WRONG_PARAMETERS_IN_REQUEST_CODE = "400 - Not correct parameters";
	
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
