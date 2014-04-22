package com.examscheduler.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="auditories")
public class Auditorie {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	public int audId;
	
	@Column(name="AUDITORY_NUMBER")
	public String audNumber;
	
	@Column(name="MAX_PERSONS")
	public int maxPerson;
	
	public int getAudId() {
		return audId;
	}
	public void setAudId(int audId) {
		this.audId = audId;
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
