package com.examscheduler.security.tools;

import java.util.Random;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.examscheduler.dto.SessionDTO;
import com.examscheduler.security.persistence.entity.UserSession;

@Component
public class SessionGenerator {
	
	static int sessionLength = 30;
	static char[] charSymbols  = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','R','S','T','U','V','W','Q','X','Y','Z'};
	
	public String generateSession(){
		return getSessionValue();
	}
	
	private String getSessionValue(){
		StringBuilder sessionString = new StringBuilder();
		for(int i=0; i<sessionLength; i++){
			Random chooseLetterNumber = new Random();
			int val = chooseLetterNumber.nextInt(2);
			if(val==1) sessionString.append(getRandomChar());
			else sessionString.append(getRandomNumber());
		}
		return sessionString.toString();
	}
	
	private char getRandomChar(){
		int randCharNum = new Random().nextInt(charSymbols.length);
		return charSymbols[randCharNum];
	}
	
	private int getRandomNumber(){
		int randomNumber = new Random().nextInt(10);
		return randomNumber;
	}
}
