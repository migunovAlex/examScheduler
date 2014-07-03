package com.examscheduler.security.session;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.examscheduler.security.persistence.SessionDAO;
import com.examscheduler.security.persistence.entity.UserSession;
import com.examscheduler.security.tools.SessionGenerator;

@Component
public class SessionComponent {
	
	private TimeThread timeThread;
	private Thread getSessionThread;
	private boolean needToInitialize = false;
	
	@Autowired
	private SessionGenerator sessionGenerator;
	@Autowired
	private SessionDAO sessionDao;
	
	public void init(){
		if(!isNeedToInitialize()){
			timeThread = new TimeThread();
			getSessionThread = new Thread(timeThread);
			getSessionThread.start();
		}
	}
	
	public String generateNewSession(){
		return sessionGenerator.generateSession();
	}

	public boolean isNeedToInitialize() {
		return needToInitialize;
	}

	public void setNeedToInitialize(boolean needToInitialize) {
		this.needToInitialize = needToInitialize;
	}
	
	public void setSessionDao(SessionDAO sessionDao) {
		this.sessionDao = sessionDao;
	}
	
	public void setSessionGenerator(SessionGenerator sessionGenerator) {
		this.sessionGenerator = sessionGenerator;
	}
	
	public void setTimeThread(TimeThread timeThread) {
		this.timeThread = timeThread;
	}

	public void setGetSessionThread(Thread getSessionThread) {
		this.getSessionThread = getSessionThread;
	}

	class TimeThread implements Runnable{
		
		private static final int SESSION_DISABLE_PERIOD = 20*60*1000;
		private static final int THREAD_SLEEP_PERIOD = 60*1000;

		public TimeThread(){}

		public void run() {
			
			while(true){
				checkSession();
				try{
		            Thread.sleep(THREAD_SLEEP_PERIOD);
		        }catch(InterruptedException e){
		        	//need to log with logger
		        }
			}
		}
		
		public void checkSession(){
			List<UserSession> listUserSession = sessionDao.getUserSessionIsActive();
	 		for(UserSession session:listUserSession){
	 			if((System.currentTimeMillis() - SESSION_DISABLE_PERIOD) > session.getLastActivity()){
	 				makeSessionInactive(session);
	 			}
	 		}
		}
		
		private void makeSessionInactive(UserSession session) {
			session.setActive(Boolean.FALSE);
			sessionDao.updateUserSession(session);
		}
	}
}
