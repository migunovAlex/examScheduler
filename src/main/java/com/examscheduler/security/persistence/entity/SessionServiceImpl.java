package com.examscheduler.security.persistence.entity;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.examscheduler.dto.ErrorData;
import com.examscheduler.dto.SessionDTO;
import com.examscheduler.exceptions.PersistentActionException;
import com.examscheduler.security.persistence.SessionDAO;
import com.examscheduler.security.session.SessionComponent;
import com.examscheduler.security.session.SessionService;
import com.examscheduler.summary.SessionSummary;

@Transactional
public class SessionServiceImpl implements SessionService {
	
	@Autowired
	private SessionComponent sessionComponent;
	
	@Autowired
	private SessionDAO sessionDao;

	public SessionSummary getNewSession(DbUser user) throws IllegalArgumentException {
		SessionSummary sessionCreateResult = new SessionSummary();
		if(user == null || user.getId() == 0){
			throw new IllegalArgumentException("No persisted user to bind session");
		}
		
		UserSession userSession = new UserSession();
		
		String newSession = generateAndCheckNewSession();
		
		userSession.setSessionValue(newSession);
		userSession.setActive(true);
		userSession.setUser(user);
		userSession.setLastActivity(Calendar.getInstance().getTimeInMillis());
		int sessionId;
		try {
			sessionId = sessionDao.saveSession(userSession);
			if(sessionId != 0){
				SessionDTO sessionDtoResult = new SessionDTO();
				sessionDtoResult.setSessionValue(userSession.getSessionValue());
				sessionDtoResult.setLastActivity(userSession.getLastActivity());
				sessionDtoResult.setActive(userSession.isActive());
				sessionCreateResult.setSession(sessionDtoResult);
			}
		} catch (PersistentActionException e) {
			e.printStackTrace();
			sessionCreateResult.getErrorData().setNumberCode(ErrorData.ERROR_WHILE_OPERATE_WITH_DB_CODE);
			sessionCreateResult.getErrorData().setDescription(ErrorData.ERROR_WHILE_OPERATE_WITH_DB_MESSAGE);
		}
		
		return sessionCreateResult;
	}

	private String generateAndCheckNewSession() {
		String generatedNewSession = null;
		boolean generatedNotTheDublicate = false;
		while(!generatedNotTheDublicate){
			generatedNewSession = sessionComponent.generateNewSession();
			generatedNotTheDublicate = checkGeneratedSession(generatedNewSession);
		}
		return generatedNewSession;
	}

	private boolean checkGeneratedSession(String generatedNewSession) {
		UserSession userSessionByValue = sessionDao.getUserSessionByValue(generatedNewSession);
		return userSessionByValue == null ? true : false;
	}

	public boolean updateSessionActivity(SessionDTO session) {
		// TODO Auto-generated method stub
		return false;
	}

	public SessionSummary getCurrentSession(SessionDTO session) {
		// TODO Auto-generated method stub
		return null;
	}

}
