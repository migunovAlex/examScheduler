package com.examscheduler.dto;

public class AuditoryDTO {
	
	public int id;
	public String audNumber;
	public int maxPerson;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAudNumber() {
		return audNumber;
	}
	public void setAudNumber(String audNumber) {
		this.audNumber = audNumber;
	}
	public int getMaxPerson() {
		return maxPerson;
	}
	public void setMaxPerson(int maxPerson) {
		this.maxPerson = maxPerson;
	}
	
	
}
