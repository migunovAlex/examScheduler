package com.examscheduler.controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieHelper {
	
	protected static final String SESSION_VALUE = "SESSION_VALUE";
	
	public String getSessionCookie(HttpServletRequest request) {
		Cookie sessionCookie = getCookie(SESSION_VALUE, request);
		if(sessionCookie!=null) return sessionCookie.getValue();
		return null;
	}
	
	private Cookie getCookie(String cookieName, HttpServletRequest request){
		Cookie[] cookies = request.getCookies();
		if(cookies==null){
			return null;
		}
		for(Cookie cookie : cookies){
			if(cookie.getName().equals(cookieName)){
				return cookie;
			}
		}
		return null;
	}
}
