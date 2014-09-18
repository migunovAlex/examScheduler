package com.examscheduler.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examscheduler.dto.ErrorData;
import com.examscheduler.dto.LessonTimeDTO;
import com.examscheduler.dto.summary.LessonsTimeListSummary;
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
	
	@RequestMapping(value="/classtime/get", method=RequestMethod.POST)
	public @ResponseBody LessonTimeDTO getLessonTime(@RequestBody Integer lessonTimeId){
		return schedulerDataService.loadLessonTime(lessonTimeId);
	}
	
	@RequestMapping(value="/classtime/all", method=RequestMethod.POST)
	public @ResponseBody LessonsTimeListSummary getListLessonTime(HttpServletRequest request){
		if (!checkUserIsStillLoggedIn(cookieHelper.getSessionCookie(request))){
			return generateExpiredSessionMessage();
		}
		return schedulerDataService.getListLessonTime();
	}

	private LessonsTimeListSummary generateExpiredSessionMessage() {
		LessonsTimeListSummary result = new LessonsTimeListSummary();
		result.getErrorData().setNumberCode(ErrorData.EXPIRED_SESSION_CODE);
		result.getErrorData().setDescription(ErrorData.EXPIRED_SESSION_MESSAGE);
		return result;
	}

	private boolean checkUserIsStillLoggedIn(String sessionCookie) {
		return userDetailService.isUserStillLoggedIn(sessionCookie);
	}
	
	public void setUserDetailService(UserDetailService userDetailService) {
		this.userDetailService = userDetailService;
	}

	public void setCookieHelper(CookieHelper cookieHelper) {
		this.cookieHelper = cookieHelper;
	}

	public void setServiceDataScheduler(SchedulerDataService serviceDataScheduler) {
		this.schedulerDataService = serviceDataScheduler;
	}
	

}
