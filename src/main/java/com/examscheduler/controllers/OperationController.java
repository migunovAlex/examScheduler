package com.examscheduler.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examscheduler.dto.LessonTimeDTO;
import com.examscheduler.service.SchedulerDataService;

@Controller
@RequestMapping("/service/secured")
public class OperationController {
	
	@Autowired
	private SchedulerDataService serviceDataScheduler;
	
	public void setServiceDataScheduler(SchedulerDataService serviceDataScheduler) {
		this.serviceDataScheduler = serviceDataScheduler;
	}

	@RequestMapping(value="/classtime/new", method=RequestMethod.POST)
	public @ResponseBody Boolean createLessonsTime(@RequestBody LessonTimeDTO lessonTime){
		Boolean createResult = serviceDataScheduler.createLessonTime(lessonTime);
		return createResult;
	}
	
	@RequestMapping(value="/classtime/edit", method=RequestMethod.POST)
	public @ResponseBody LessonTimeDTO updLessonsTime(@RequestBody LessonTimeDTO lessonTime){
		LessonTimeDTO updLessonTime = serviceDataScheduler.updateLessonTime(lessonTime);		
		return updLessonTime;
	}
	
	@RequestMapping(value="/classtime/delete", method=RequestMethod.POST)
	public @ResponseBody Boolean deleteLessonsTime(@RequestBody Integer lessonTimeId){
		Boolean delLessonTime = serviceDataScheduler.deleteLessonTime(lessonTimeId);
		return delLessonTime;
	}
	
	@RequestMapping(value="/classtime/all", method=RequestMethod.POST)
	public @ResponseBody List<LessonTimeDTO> getListLessonTime(){
		List<LessonTimeDTO> listLessonTime = serviceDataScheduler.getListLessonTime();
		return listLessonTime;
	}
	
	@RequestMapping(value="/classtime/get", method=RequestMethod.POST)
	public @ResponseBody LessonTimeDTO getLessonTime(@RequestBody Integer lessonTimeId){
		LessonTimeDTO lessonTimeDTO = serviceDataScheduler.loadLessonTime(lessonTimeId);
		return lessonTimeDTO;
	}

}
