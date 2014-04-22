package com.examscheduler.dto;

public class LessonTimeDTO {

	private int id;
	private int lessonNumber;
	private int timeStart;
	private int timeEnd;
	
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
	public int getTimeStart() {
		return timeStart;
	}
	public void setTimeStart(int timeStart) {
		this.timeStart = timeStart;
	}
	public int getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(int timeEnd) {
		this.timeEnd = timeEnd;
	}
	
	
	
}
