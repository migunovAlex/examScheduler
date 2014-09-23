package com.examscheduler.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examscheduler.dto.AuditoryDTO;
import com.examscheduler.dto.ErrorData;
import com.examscheduler.dto.LessonTimeDTO;
import com.examscheduler.dto.summary.AbstractSummary;
import com.examscheduler.dto.summary.LessonsTimeListSummary;
import com.examscheduler.security.service.UserDetailService;
import com.examscheduler.service.SchedulerDataService;
import com.examscheduler.summary.OperationResultSummary;

@Controller
@RequestMapping("/service/secured")
public class OperationController {
	
	@Autowired
	private SchedulerDataService schedulerDataService;
	@Autowired
	private UserDetailService userDetailService;
	@Autowired
	private CookieHelper cookieHelper;
	
	@RequestMapping(value="/classtime", method=RequestMethod.POST)
	public @ResponseBody AbstractSummary createLessonsTime(HttpServletRequest request, @ModelAttribute LessonTimeDTO lessonTime){
		if (!checkUserIsStillLoggedIn(request)){
			return generateExpiredSessionMessage(new OperationResultSummary());
		}
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
	
	@RequestMapping(value="/classtime/get", method=RequestMethod.POST)
	public @ResponseBody LessonTimeDTO getLessonTime(@RequestBody Integer lessonTimeId){
		return schedulerDataService.loadLessonTime(lessonTimeId);
	}
	
	@RequestMapping(value="/classtime", method=RequestMethod.GET)
	public @ResponseBody AbstractSummary getListLessonTime(HttpServletRequest request){
		if (!checkUserIsStillLoggedIn(request)){
			return generateExpiredSessionMessage(new LessonsTimeListSummary());
		}
		return schedulerDataService.getListLessonTime();
	}

	private AbstractSummary generateExpiredSessionMessage(AbstractSummary abstractSummary) {
		ErrorData errorData = new ErrorData();
		errorData.setNumberCode(ErrorData.EXPIRED_SESSION_CODE);
		errorData.setDescription(ErrorData.EXPIRED_SESSION_MESSAGE);
		abstractSummary.setErrorData(errorData);
		return abstractSummary;
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
			return generateExpiredSessionMessage(new OperationResultSummary());
		}
		return schedulerDataService.createAuditory(auditory);
	}
	
	@RequestMapping(value="/auditory/edit", method=RequestMethod.POST)
	public @ResponseBody AbstractSummary updateAuditoriesTimes(HttpServletRequest request, @RequestBody AuditoryDTO auditoryDTO){
		if(!checkUserIsStillLoggedIn(request)){
			return generateExpiredSessionMessage(new OperationResultSummary());
		}
		return schedulerDataService.updateAuditory(auditoryDTO);
	}
	
	@RequestMapping(value="/auditory/delete", method=RequestMethod.POST)
	public @ResponseBody AbstractSummary deleteAuditory(HttpServletRequest request, @RequestBody Integer auditoryId){
		if(!checkUserIsStillLoggedIn(request)){
			return generateExpiredSessionMessage(new OperationResultSummary());
		}
		return schedulerDataService.deleteAuditory(auditoryId);
	}
	
	@RequestMapping(value="/auditory/all", method=RequestMethod.POST)
	public @ResponseBody AbstractSummary getAuditories(HttpServletRequest request){
		if(!checkUserIsStillLoggedIn(request)){
			return generateExpiredSessionMessage(new OperationResultSummary());
		}
		return schedulerDataService.getListAuditory();
	}
	

	public void setCookieHelper(CookieHelper cookieHelper) {
		this.cookieHelper = cookieHelper;
	}

	public void setServiceDataScheduler(SchedulerDataService serviceDataScheduler) {
		this.schedulerDataService = serviceDataScheduler;
	}
	

}
