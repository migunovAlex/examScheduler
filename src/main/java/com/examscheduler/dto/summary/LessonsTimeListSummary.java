package com.examscheduler.dto.summary;

import java.util.List;

import com.examscheduler.dto.ErrorData;
import com.examscheduler.dto.LessonTimeDTO;

public class LessonsTimeListSummary {
	
	private List<LessonTimeDTO> lessonsTimeList;
	private ErrorData errorData;
	
	public LessonsTimeListSummary(){
		errorData = new ErrorData();
	}
	
	public List<LessonTimeDTO> getLessonsTimeList() {
		return lessonsTimeList;
	}
	public void setLessonsTimeList(List<LessonTimeDTO> lessonsTimeList) {
		this.lessonsTimeList = lessonsTimeList;
	}
	public ErrorData getErrorData() {
		return errorData;
	}
	public void setErrorData(ErrorData errorData) {
		this.errorData = errorData;
	}
	
}
