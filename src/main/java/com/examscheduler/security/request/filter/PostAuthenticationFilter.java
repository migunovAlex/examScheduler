package com.examscheduler.security.request.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.examscheduler.security.persistence.UserDao;
import com.examscheduler.security.persistence.entity.DbUser;
import com.examscheduler.security.service.SessionService;
import com.examscheduler.summary.SessionSummary;

public class PostAuthenticationFilter implements AuthenticationSuccessHandler{
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private UserDao userDao;


	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication auth) throws IOException,
			ServletException {
		String userName = request.getParameter("j_username");
		String password = request.getParameter("j_password");
		if(userName!=null && password!=null){
			DbUser user = userDao.searchDatabase(userName);
			if(user!=null){	
				SessionSummary newSession = sessionService.getNewSession(user);
				if(newSession.getSession()!=null){
					response.addCookie(new Cookie("SESSION_VALUE", newSession.getSession().getSessionValue()));
				}
			}
			
		}
	}

	public SessionService getSessionService() {
		return sessionService;
	}
	
	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
}
