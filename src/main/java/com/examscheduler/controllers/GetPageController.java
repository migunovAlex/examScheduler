package com.examscheduler.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/pages")
public class GetPageController {
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String getLoginPage(){
		return "loginPage";
	}
	
	@RequestMapping(value="/mainpage", method=RequestMethod.GET)
	public String getUnauthorizedMainPage(){
		return "mainPage";
	}
	
	@RequestMapping(value="/secured/mainpage", method=RequestMethod.GET)
	public String getAuthorizedMainPage(ModelMap model){
		model.addAttribute("userSession", "FAKE_USER_SESSION");
		return "mainPage";
	}
}
