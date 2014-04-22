package com.examscheduler.controllers;

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
	
//	@Autowired
//	private SchedulerDataService serviceDataScheduler;
	//private PersistenceDAO persistence;
	
	@RequestMapping(value="/classtime/new", method=RequestMethod.POST)
	//public @ResponseBody LessonTimeDTO getLessonsTime(@RequestBody LessonTimeDTO lessonTime){
	//@ResponseBody
	public @ResponseBody LessonTimeDTO getLessonsTime(@RequestBody LessonTimeDTO lessonTime){
		LessonTimeDTO lessonTimeResult = new LessonTimeDTO();
		
		System.out.println("Service has been invoked");
		
		if(lessonTime!=null){
			System.out.println("Recieved params - " + lessonTime);
		}else{
			System.out.println("ERROR");
		}
		
/*		LessonsTime lessonTime = new LessonsTime();
		
		public @ResponseBody LessonTimeDTO getLessonsTime(@RequestBody (value="lessonNum", required = false) Integer lessonNum,@RequestParam(value="startTime", required = false) Integer startTime, @RequestParam(value="endTime", required = false) Integer endTime, ModelMap model){
		
		lessonTimeResult.setLessonNumber(lessonNum);
		lessonTimeResult.setTimeStart(startTime);
		lessonTimeResult.setTimeEnd(endTime);
		lessonTime.setLessonNumber(lessonTimeResult.getLessonNumber());
		lessonTime.setTimeStart(Long.parseLong("5454654646464"));
		lessonTime.setTimeEnd(Long.parseLong("5454654646464"));
		persistence.createLessonsTime(lessonTime); */
		
		
		return lessonTimeResult;
	}
/*	
	public LessonTimeDTO getLessonTime(@RequestBody LessonTimeDTO lessonTime){
		return new LessonTimeDTO();
	}*/
}
