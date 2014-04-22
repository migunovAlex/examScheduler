package com.examscheduler.dto;

public class LessonTimeDTO {

	private Long lessonNumber;
	private Long timeStart;
	private Long timeEnd;
	
	public Long getLessonNumber() {
		return lessonNumber;
	}
	public void setLessonNumber(Long lessonNumber) {
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
