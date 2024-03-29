package terhelper;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

public class AppConfig {
	private static AppConfig self;
	private String pathToConfigFile; 
	private Document config;
	
	public static AppConfig getInstance() {
		if (null == self) {
			self = new AppConfig();
		}	
		return self;
	}
	
	public AppConfig()  {
		try {
			pathToConfigFile = "/sites/terhelper/app-config.xml";
			config = DocumentBuilderFactory
				.newInstance()
				.newDocumentBuilder()
				.parse(new File(pathToConfigFile));
			config.normalize();
		} catch (Exception e) {
			e.printStackTrace();
			//System.exit(0);
		}	
	}
	
	public String getUserIdForApi() {
		return config.getElementsByTagName("userid").item(0).getTextContent();
	}
	
	public String getSecretForApi() {
		return config.getElementsByTagName("secret").item(0).getTextContent();
	}
	
	public String getRedisPassword() {
		return config.getElementsByTagName("redispassword").item(0).getTextContent();
	}
	
	public String getEnv() {
		return Env.PROD;		
	}
}