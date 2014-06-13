package com.examscheduler.controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examscheduler.security.service.SessionService;
import com.examscheduler.security.service.UserDetailService;

@Controller
@RequestMapping("/pages")
public class GetPageController {
	
	private static final String USER_NAME_PARAM = "userName";

	protected static final String SESSION_VALUE = "SESSION_VALUE";

	protected static final String USER_SESSION_PARAM = "userSession";
	
	@Autowired
	private SessionService sessionService;
	@Autowired
	private UserDetailService userDetailService;
	
	
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
		String sessionCookie = getSessionCookie(request);
		//need to check session for expiration
		model.addAttribute(USER_SESSION_PARAM, sessionCookie);
		
		UserDetails userDetails = userDetailService.getUserDetailsBySession(sessionCookie);
		if(userDetails!=null){
			model.addAttribute(USER_NAME_PARAM, userDetails.getUsername());
		}
		
		return "mainPage";
	}
	
	private String getSessionCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies){
			if(cookie.getName().equals(SESSION_VALUE)){
				return cookie.getValue();
			}
		}
		return null;
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
	

	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}

	public void setUserDetailService(UserDetailService userDetailService) {
		this.userDetailService = userDetailService;
	}
}
