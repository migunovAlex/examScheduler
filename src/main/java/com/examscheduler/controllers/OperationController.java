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
	private SchedulerDataService schedulerDataService;
	
	public void setServiceDataScheduler(SchedulerDataService serviceDataScheduler) {
		this.schedulerDataService = serviceDataScheduler;
	}

	@RequestMapping(value="/classtime/new", method=RequestMethod.POST)
	public @ResponseBody Boolean createLessonsTime(@RequestBody LessonTimeDTO lessonTime){
		return schedulerDataService.createLessonTime(lessonTime);
	}
	
	@RequestMapping(value="/classtime/edit", method=RequestMethod.POST)
	public @ResponseBody LessonTimeDTO updLessonsTime(@RequestBody LessonTimeDTO lessonTime){
		return schedulerDataService.updateLessonTime(lessonTime);
	}
	
	@RequestMapping(value="/classtime/delete", method=RequestMethod.POST)
	public @ResponseBody Boolean deleteLessonsTime(@RequestBody Integer lessonTimeId){
		return schedulerDataService.deleteLessonTime(lessonTimeId);
	}
	
	@RequestMapping(value="/classtime/all", method=RequestMethod.POST)
	public @ResponseBody List<LessonTimeDTO> getListLessonTime(){
		return schedulerDataService.getListLessonTime();
	}
	
	@RequestMapping(value="/classtime/get", method=RequestMethod.POST)
	public @ResponseBody LessonTimeDTO getLessonTime(@RequestBody Integer lessonTimeId){
		return schedulerDataService.loadLessonTime(lessonTimeId);
	}

}
