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
	
	private AppConfig()  {
		try {
			//@TODO 
			/** 
			  pathToConfigFile = (null == System.getenv("test")) 
					? "/sites/conf/terhelper/app-config.xml"
					: System.getenv("test") + "/terhelper/app-config.xml"; 
			**/
			
			pathToConfigFile = "/sites/conf/terhelper/app-config.xml";
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
	
	public String getEnv() {
		String env = config.getElementsByTagName("env").item(0).getTextContent();
		return ((null == env) || env.isEmpty()) ? Env.PROD : env;
		
	}
}