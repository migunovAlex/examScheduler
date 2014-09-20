package com.examscheduler.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="auditories")
public class Auditory {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	public int id;
	
	@Column(name="AUDITORY_NUMBER")
	public String audNumber;
	
	@Column(name="MAX_PERSONS")
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
	@Override
	public String toString() {
		return "ID - " + id + "; auditory number - " + audNumber + "; maxPersons - " + maxPerson;
	}
	
	
	
}
