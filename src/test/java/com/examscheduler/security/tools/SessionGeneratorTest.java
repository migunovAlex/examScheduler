package com.examscheduler.security.tools;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SessionGeneratorTest {
	
	private SessionGenerator sessionGenerator;
	
	@Before
	public void setUp(){
		sessionGenerator = new SessionGenerator();
	}
	
	@Test
	public void shouldReturnNewSession(){
		String generatedSession = sessionGenerator.generateSession();
		assertNotNull(generatedSession);
		assertEquals(generatedSession.length(), SessionGenerator.sessionLength);
	}

}
