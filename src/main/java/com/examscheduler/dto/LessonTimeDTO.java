package com.examscheduler.dto;

public class LessonTimeDTO {

	private int id;
	private int lessonNumber;
	private Long timeStart;
	private Long timeEnd;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLessonNumber() {
		return lessonNumber;
	}
	public void setLessonNumber(int lessonNumber) {
		this.lessonNumber = lessonNumber;
	}
	public Long getTimeStart() {
		return timeStart;
	}
	public void setTimeStart(Long timeStart) {
		this.timeStart = timeStart;
	}
	public Long getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(Long timeEnd) {
		this.timeEnd = timeEnd;
	}
	
	
	
}
