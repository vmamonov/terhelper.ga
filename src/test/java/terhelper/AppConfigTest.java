package terhelper;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class AppConfigTest {
	AppConfig config = AppConfig.getInstance();
	
	@Test 
	public void testGetInstance() { 
		assertTrue(config instanceof AppConfig);
	}
	  
	@Test
	public void testGetUserIdForApi() {
		String userId = config.getUserIdForApi();
		assertNotNull(userId);
		assertTrue(userId.length() > 0);
	}

	@Test
	public void testGetSecretForApi() {
		String secret = config.getSecretForApi();
		assertNotNull(secret);
		assertTrue(secret.length() > 0);
	} 

	@Test
	public void testGetEnv() {
		String env = config.getEnv();
		assertNotNull(env);
		assertTrue(env.equals(Env.PROD) || env.equals(Env.TEST));
	}

}
