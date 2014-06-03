package com.examscheduler.security.session;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.examscheduler.security.tools.SessionGenerator;

@RunWith(MockitoJUnitRunner.class)
public class SessionComponentTest {
	
	private static final String FAKE_GENERATED_SESSION = "FAKE_GENERATED_SESSION";

	@Mock
	private SessionGenerator sessionGenerator;
	
	private SessionComponent testInstance;
	
	@Before
	public void setUp(){
		testInstance = new SessionComponent();
		testInstance.setSessionGenerator(sessionGenerator);
		when(sessionGenerator.generateSession()).thenReturn(FAKE_GENERATED_SESSION);
	}
	
	@Test
	public void shouldReturnGeneratedSession(){
		String generateNewSession = testInstance.generateNewSession();
		assertNotNull(generateNewSession);
	}

}
