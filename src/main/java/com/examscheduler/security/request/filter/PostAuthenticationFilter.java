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

public class PostAuthenticationFilter implements AuthenticationSuccessHandler {

	protected static final String SESSION_PARAM = "SESSION_VALUE";

	protected static final String PASSWORD_PARAM = "j_password";

	protected static final String USERNAME_PARAM = "j_username";

	@Autowired
	private SessionService sessionService;
	
	private String forwardURL;

	public void setForwardURL(String forwardURL) {
		this.forwardURL = forwardURL;
	}

	@Autowired
	private UserDao userDao;

	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {
		SessionSummary newSession = null;
		String userName = request.getParameter(USERNAME_PARAM);
		String password = request.getParameter(PASSWORD_PARAM);
		if (userName != null && password != null) {
			DbUser user = userDao.searchDatabase(userName);
			if (user != null) {
				newSession = sessionService.getNewSession(user);
				if (newSession.getSession() != null) {
					response.addCookie(new Cookie(SESSION_PARAM, newSession
							.getSession().getSessionValue()));
				}
			}

		}
		response.sendRedirect(request.getContextPath()+forwardURL);
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
