package com.examscheduler.dto;

public class AuditoriesTimesDTO {
	
	private Integer id;
	private Integer lessonNumber;
	private Integer timeStartMinutes;
	private Integer timeEndMinutes;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getLessonNumber() {
		return lessonNumber;
	}
	public void setLessonNumber(Integer lessonNumber) {
		this.lessonNumber = lessonNumber;
	}
	public Integer getTimeStartMinutes() {
		return timeStartMinutes;
	}
	public void setTimeStartMinutes(Integer timeStartMinutes) {
		this.timeStartMinutes = timeStartMinutes;
	}
	public Integer getTimeEndMinutes() {
		return timeEndMinutes;
	}
	public void setTimeEndMinutes(Integer timeEndMinutes) {
		this.timeEndMinutes = timeEndMinutes;
	}
	

}
