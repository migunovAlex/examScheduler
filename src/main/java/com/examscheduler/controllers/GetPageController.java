package com.examscheduler.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/pages")
public class GetPageController {
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String getLoginPage(){
		return "loginPage";
	}
	
}