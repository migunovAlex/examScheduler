package com.examscheduler.security.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.examscheduler.dto.ErrorData;
import com.examscheduler.dto.SessionDTO;
import com.examscheduler.exceptions.PersistentActionException;
import com.examscheduler.security.persistence.SessionDAO;
import com.examscheduler.security.persistence.entity.DbUser;
import com.examscheduler.security.persistence.entity.UserSession;
import com.examscheduler.security.session.SessionComponent;
import com.examscheduler.summary.OperationResultSummary;
import com.examscheduler.summary.SessionSummary;

@Transactional
public class SessionServiceImpl implements SessionService {
	
	@Autowired
	private SessionComponent sessionComponent;
	
	@Autowired
	private SessionDAO sessionDao;

	public SessionSummary getNewSession(DbUser user){
		SessionSummary result = new SessionSummary();
		if(user == null || user.getId() == 0){
			result.getErrorData().setNumberCode(ErrorData.WRONG_PARAMETERS_IN_REQUEST_CODE);
			 result.getErrorData().setDescription(ErrorData.WRONG_PARAMETERS_IN_REQUEST_MESSAGE);
			 return result;
		}
		
		UserSession userSession = new UserSession();
		
		String newSession = generateAndCheckNewSession();
		
		userSession.setSessionValue(newSession);
		userSession.setActive(true);
		userSession.setUser(user);
		userSession.setLastActivity(Calendar.getInstance().getTimeInMillis());
		try {
				getSessionDao().saveSession(userSession);
				SessionDTO sessionDtoResult = new SessionDTO();
				sessionDtoResult.setSessionValue(userSession.getSessionValue());
				sessionDtoResult.setLastActivity(userSession.getLastActivity());
				sessionDtoResult.setActive(userSession.isActive());
				result.setSession(sessionDtoResult);
		} catch (PersistentActionException e) {
//			e.printStackTrace();
			result.getErrorData().setNumberCode(ErrorData.ERROR_WHILE_OPERATE_WITH_DB_CODE);
			result.getErrorData().setDescription(ErrorData.ERROR_WHILE_OPERATE_WITH_DB_MESSAGE);
		}
		
		return result;
	}

	private String generateAndCheckNewSession() {
		String generatedNewSession = null;
		boolean generatedNotTheDublicate = false;
		while(!generatedNotTheDublicate){
			generatedNewSession = getSessionComponent().generateNewSession();
			generatedNotTheDublicate = checkGeneratedSession(generatedNewSession);
		}
		return generatedNewSession;
	}

	private boolean checkGeneratedSession(String generatedNewSession) {
		UserSession userSessionByValue = getSessionDao().getUserSessionByValue(generatedNewSession);
		return userSessionByValue == null ? true : false;
	}

	public OperationResultSummary updateSessionActivity(SessionDTO session) {
		OperationResultSummary result = new OperationResultSummary();
		if(session.getSessionValue() == null){
			result.getErrorData().setNumberCode(ErrorData.WRONG_PARAMETERS_IN_REQUEST_CODE);
			 result.getErrorData().setDescription(ErrorData.WRONG_PARAMETERS_IN_REQUEST_MESSAGE);
			 result.setOperationResult(false);
			 return result;
		}
		 UserSession foundSession = sessionDao.getUserSessionByValue(session.getSessionValue());
		 if(foundSession==null){
			 result.getErrorData().setNumberCode(ErrorData.NO_SUCH_SESSION_CODE);
			 result.getErrorData().setDescription(ErrorData.NO_SUCH_SESSION_MESSAGE);
			 result.setOperationResult(false);
			 return result;
		 }
		 if(!foundSession.isActive()){
			 result.getErrorData().setNumberCode(ErrorData.EXPIRED_SESSION_CODE);
			 result.getErrorData().setDescription(ErrorData.EXPIRED_SESSION_MESSAGE);
			 return result;
		 }
		 foundSession.setLastActivity(Calendar.getInstance().getTimeInMillis());
		 boolean updateUserSession = sessionDao.updateUserSession(foundSession);
		 if(!updateUserSession){
			 result.getErrorData().setNumberCode(ErrorData.ERROR_WHILE_OPERATE_WITH_DB_CODE);
			 result.getErrorData().setDescription(ErrorData.ERROR_WHILE_OPERATE_WITH_DB_MESSAGE);
			 result.setOperationResult(false);
			 return result;
		 }
		 session.setLastActivity(foundSession.getLastActivity());
		 result.setOperationResult(true);
		return result;
	}

	public SessionSummary getCurrentSession(SessionDTO session) {
		SessionSummary result = new SessionSummary();
		if(session == null || session.getSessionValue() == null){
			result.getErrorData().setNumberCode(ErrorData.WRONG_PARAMETERS_IN_REQUEST_CODE);
			 result.getErrorData().setDescription(ErrorData.WRONG_PARAMETERS_IN_REQUEST_MESSAGE);
			 return result;
		}
		
		UserSession currentSession = sessionDao.getUserSessionByValue(session.getSessionValue());
		if(currentSession==null){
			 result.getErrorData().setNumberCode(ErrorData.NO_SUCH_SESSION_CODE);
			 result.getErrorData().setDescription(ErrorData.NO_SUCH_SESSION_MESSAGE);
			 return result;
		}
		
		SessionDTO sessionDto = new SessionDTO();
		sessionDto.setActive(currentSession.isActive());
		sessionDto.setLastActivity(currentSession.getLastActivity());
		sessionDto.setSessionValue(currentSession.getSessionValue());
		result.setSession(sessionDto);
		
		return result;
	}

	public SessionComponent getSessionComponent() {
		return sessionComponent;
	}

	public void setSessionComponent(SessionComponent sessionComponent) {
		this.sessionComponent = sessionComponent;
	}

	public SessionDAO getSessionDao() {
		return sessionDao;
	}

	public void setSessionDao(SessionDAO sessionDao) {
		this.sessionDao = sessionDao;
	}

	public OperationResultSummary logout(String session) {
		OperationResultSummary result = new OperationResultSummary();
		UserSession currentSession = sessionDao.getUserSessionByValue(session);
		if(currentSession==null){
			 result.setOperationResult(Boolean.FALSE);
			 result.getErrorData().setNumberCode(ErrorData.NO_SUCH_SESSION_CODE);
			 result.getErrorData().setDescription(ErrorData.NO_SUCH_SESSION_MESSAGE);
			 return result;
		}
		if(!currentSession.isActive()){
			result.setOperationResult(Boolean.FALSE);
			 result.getErrorData().setNumberCode(ErrorData.EXPIRED_SESSION_CODE);
			 result.getErrorData().setDescription(ErrorData.EXPIRED_SESSION_MESSAGE);
			 return result;
		}
		currentSession.setActive(Boolean.FALSE);
		currentSession.setLastActivity(Calendar.getInstance().getTimeInMillis());
		boolean updateUserSession = sessionDao.updateUserSession(currentSession);
		if(!updateUserSession){
			result.setOperationResult(Boolean.FALSE);
			result.getErrorData().setNumberCode(ErrorData.ERROR_WHILE_EXECUTE_OPERATION_CODE);
			result.getErrorData().setDescription(ErrorData.ERROR_WHILE_EXECUTE_OPERATION_MESSAGE);
			return result;
		}
		return result;
	}

}
