package com.examscheduler.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;

import com.examscheduler.service.SchedulerDataService;

@Controller
@RequestMapping("/service/secured/classtime")
public class OperationController {
	
	@Autowired
	private SchedulerDataService serviceLayer;
	
	@RequestMapping(value="/getauditories", method=RequestMethod.POST)
	public String getAuditories(@RequestParam(value="audId") Integer audId, ModelMap model){
		return "";
	}
	
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public String getLessonsTime(@RequestParam(value="startTime") Integer startTime, @RequestParam(value="endTime") Integer endTime, ModelMap model){
		return "";
	}
		
}
