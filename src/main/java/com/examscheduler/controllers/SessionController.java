package com.examscheduler.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examscheduler.controllers.tools.CookieHelper;
import com.examscheduler.security.service.SessionService;
import com.examscheduler.summary.OperationResultSummary;

@Controller
@RequestMapping("service/secured/session")
public class SessionController {
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private CookieHelper cookieHelper;

	@RequestMapping(value="/logout", method=RequestMethod.POST)
	public @ResponseBody OperationResultSummary logout(HttpServletRequest request) throws IOException{
		return sessionService.logout(cookieHelper.getSessionCookie(request));
	}
	
	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}

	public void setCookieHelper(CookieHelper cookieHelper) {
		this.cookieHelper = cookieHelper;
	}

}
