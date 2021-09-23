package terhelper;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class AppConfigTest {
	@Test
	public void testGetInstance() throws SAXException, IOException, ParserConfigurationException { 
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
