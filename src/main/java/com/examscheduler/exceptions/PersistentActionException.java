package com.examscheduler.exceptions;

public class PersistentActionException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public PersistentActionException(){
		super();
	}
	
	public PersistentActionException(String message){
		super(message);
	}

}
