package terhelper;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

public class AppConfigTest {
	@Test 
	public void testGetInstance() { 
		assertTrue(AppConfig.getInstance() instanceof AppConfig);
	}
	  
	@Test
	public void testGetUserIdForApi() {
		String userId = AppConfig.getInstance().getUserIdForApi();
		assertNotNull(userId);
		assertTrue(userId.length() > 0);
	}

	@Test
	public void testGetSecretForApi() {
		String secret = AppConfig.getInstance().getSecretForApi();
		assertNotNull(secret);
		assertTrue(secret.length() > 0);
	} 

	@Test
	public void testGetEnv() {
		String env = AppConfig.getInstance().getEnv();
		assertNotNull(env);
		assertTrue(env.equals(Env.PROD) || env.equals(Env.TEST));
	}

}
