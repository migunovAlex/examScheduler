package com.examscheduler.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examscheduler.security.service.UserDetailService;

@Controller
@RequestMapping("/pages")
public class GetPageController {
	
	protected static final String USER_NAME_PARAM = "userName";

	protected static final String USER_SESSION_PARAM = "userSession";
	
	@Autowired
	private UserDetailService userDetailService;
	@Autowired
	private CookieHelper cookieHelper;
	
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String getLoginPage(){
		return "loginPage";
	}
	
	@RequestMapping(value="/mainpage", method=RequestMethod.GET)
	public String getUnauthorizedMainPage(){
		return "mainPage";
	}
	
	@RequestMapping(value="/secured/mainpage", method=RequestMethod.GET)
	public String getAuthorizedMainPage(HttpServletRequest request, ModelMap model){
		String sessionCookie = cookieHelper.getSessionCookie(request);
		//need to check session for expiration
		model.addAttribute(USER_SESSION_PARAM, sessionCookie);
		
		UserDetails userDetails = userDetailService.getUserDetailsBySession(sessionCookie);
		if(userDetails!=null){
			model.addAttribute(USER_NAME_PARAM, userDetails.getUsername());
		}
		
		return "mainPage";
	}
	
	@RequestMapping(value="/secured/lessontime", method=RequestMethod.GET)
	public String getLessonTimePage(){
		return "lessonTime";
	}
	
	@RequestMapping(value="/secured/teacher", method=RequestMethod.GET)
	public String getTeacherPage(){
		return "teacherPage";
	}
	
	@RequestMapping(value="/secured/lessons", method=RequestMethod.GET)
	public String getLessonsPage(){
		return "lessonPage";
	}
	
	public void setUserDetailService(UserDetailService userDetailService) {
		this.userDetailService = userDetailService;
	}

	public void setCookieHelper(CookieHelper cookieHelper) {
		this.cookieHelper = cookieHelper;
	}
}
