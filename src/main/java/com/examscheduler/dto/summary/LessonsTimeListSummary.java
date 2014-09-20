package com.examscheduler.dto.summary;

import java.util.List;

import com.examscheduler.dto.LessonTimeDTO;

public class LessonsTimeListSummary extends AbstractSummary{
	
	private List<LessonTimeDTO> lessonsTimeList;
	
	public LessonsTimeListSummary(){
		super();
	}
	
	public List<LessonTimeDTO> getLessonsTimeList() {
		return lessonsTimeList;
	}
	public void setLessonsTimeList(List<LessonTimeDTO> lessonsTimeList) {
		this.lessonsTimeList = lessonsTimeList;
	}
	
}
