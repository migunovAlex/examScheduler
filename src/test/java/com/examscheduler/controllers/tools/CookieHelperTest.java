package com.examscheduler.controllers.tools;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.examscheduler.controllers.tools.CookieHelper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CookieHelperTest {
	
	private static final String FAKE_SESSION = "FAKE_SESSION";
	@Mock
	private HttpServletRequest request;
	
	private CookieHelper testInstance;
	
	@Before
	public void setUp(){
		testInstance = new CookieHelper();
	}
	
	
	@Test
	public void shouldNotGetSessionFromCookie(){
		when(request.getCookies()).thenReturn(new Cookie[0]);
		String sessionCookie = testInstance.getSessionCookie(request);
		assertNull(sessionCookie);
	}
	
	@Test
	public void shouldGetSessionFromCookie(){
		when(request.getCookies()).thenReturn(getCookieList());
		String sessionCookie = testInstance.getSessionCookie(request);
		assertNotNull(sessionCookie);
		assertEquals(sessionCookie, FAKE_SESSION);
	}
	
	@Test
	public void shouldReturnNullIfNoCookies(){
		when(request.getCookies()).thenReturn(null);
		String sessionCookie = testInstance.getSessionCookie(request);
		assertNull(sessionCookie);
	}

	private Cookie[] getCookieList() {
		Cookie[] cookieArray = new Cookie[2];
		cookieArray[0] = new Cookie("FAKE_COOKIE", "FAKE_COOKIE_VALUE");
		cookieArray[1] = new Cookie(CookieHelper.SESSION_VALUE, FAKE_SESSION);
		return cookieArray;
	}
	
	

}
