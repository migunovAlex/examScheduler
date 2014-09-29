package com.examscheduler.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examscheduler.controllers.tools.CookieHelper;
import com.examscheduler.dto.AuditoryDTO;
import com.examscheduler.dto.LessonTimeDTO;
import com.examscheduler.dto.summary.AbstractSummary;
import com.examscheduler.helpers.ResponseSummaryCreator;
import com.examscheduler.security.service.UserDetailService;
import com.examscheduler.service.SchedulerDataService;

@Controller
@RequestMapping("/service/secured")
public class OperationController {
	
	@Autowired
	private SchedulerDataService schedulerDataService;
	@Autowired
	private UserDetailService userDetailService;
	@Autowired
	private CookieHelper cookieHelper;
	@Autowired
	private ResponseSummaryCreator responseSummaryCreator;
	
	@RequestMapping(value="/classtime/new", method=RequestMethod.POST)
	public @ResponseBody AbstractSummary createLessonsTime(HttpServletRequest request, @ModelAttribute LessonTimeDTO lessonTime){
		if (!checkUserIsStillLoggedIn(request)){
			return responseSummaryCreator.generateExpiredSessionMessageResponse();
		}
		return schedulerDataService.createLessonTime(lessonTime);
	}
	
	@RequestMapping(value="/classtime/edit", method=RequestMethod.POST)
	public @ResponseBody AbstractSummary updLessonsTime(@ModelAttribute LessonTimeDTO lessonTime){
		return schedulerDataService.updateLessonTime(lessonTime);
	}
	
	@RequestMapping(value="/classtime/delete", method=RequestMethod.POST)
	public @ResponseBody AbstractSummary deleteLessonsTime(@ModelAttribute(value="id") Integer lessonTimeId){
		return schedulerDataService.deleteLessonTime(lessonTimeId);
	}
	
	@RequestMapping(value="/classtime/get", method=RequestMethod.POST)
	public @ResponseBody AbstractSummary getLessonTime(@ModelAttribute(value="id") Integer lessonTimeId){
		return schedulerDataService.loadLessonTime(lessonTimeId);
	}
	
	@RequestMapping(value="/classtime/all", method=RequestMethod.GET)
	public @ResponseBody AbstractSummary getListLessonTime(HttpServletRequest request){
		if (!checkUserIsStillLoggedIn(request)){
			return responseSummaryCreator.generateExpiredSessionMessageResponse();
		}
		return schedulerDataService.getListLessonTime();
	}
	
	private boolean checkUserIsStillLoggedIn(HttpServletRequest request) {
		return userDetailService.isUserStillLoggedIn(cookieHelper.getSessionCookie(request));
	}
	
	public void setUserDetailService(UserDetailService userDetailService) {
		this.userDetailService = userDetailService;
	}
	
	@RequestMapping(value="/auditory/new", method=RequestMethod.POST)
	public @ResponseBody AbstractSummary createAuditoriesTimes(HttpServletRequest request, @RequestBody AuditoryDTO auditory){
		if(!checkUserIsStillLoggedIn(request)){
			return responseSummaryCreator.generateExpiredSessionMessageResponse();
		}
		return schedulerDataService.createAuditory(auditory);
	}
	
	@RequestMapping(value="/auditory/edit", method=RequestMethod.POST)
	public @ResponseBody AbstractSummary updateAuditoriesTimes(HttpServletRequest request, @RequestBody AuditoryDTO auditoryDTO){
		if(!checkUserIsStillLoggedIn(request)){
			return responseSummaryCreator.generateExpiredSessionMessageResponse();
		}
		return schedulerDataService.updateAuditory(auditoryDTO);
	}
	
	@RequestMapping(value="/auditory/delete", method=RequestMethod.POST)
	public @ResponseBody AbstractSummary deleteAuditory(HttpServletRequest request, @RequestBody Integer auditoryId){
		if(!checkUserIsStillLoggedIn(request)){
			return responseSummaryCreator.generateExpiredSessionMessageResponse();
		}
		return schedulerDataService.deleteAuditory(auditoryId);
	}
	
	@RequestMapping(value="/auditory/all", method=RequestMethod.POST)
	public @ResponseBody AbstractSummary getAuditories(HttpServletRequest request){
		if(!checkUserIsStillLoggedIn(request)){
			return responseSummaryCreator.generateExpiredSessionMessageResponse();
		}
		return schedulerDataService.getListAuditory();
	}

	public void setCookieHelper(CookieHelper cookieHelper) {
		this.cookieHelper = cookieHelper;
	}

	public void setServiceDataScheduler(SchedulerDataService serviceDataScheduler) {
		this.schedulerDataService = serviceDataScheduler;
	}
	
	public void setResponseSummaryCreator(ResponseSummaryCreator responseSummaryCreator) {
		this.responseSummaryCreator = responseSummaryCreator;
	}



}
