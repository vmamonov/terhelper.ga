package terhelper;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class AppConfig {
	private static AppConfig self;
	private String pathToConfigFile = Env.APP_PATH_ROOT + "../../app-config.xml"; 
	private Document config;
	
	public static AppConfig getInstance() {
		if (null == self) {
			self = new AppConfig();
		}	
		return self;
	}
	
	private AppConfig()  {
		try {
			config = DocumentBuilderFactory
					.newInstance()
					.newDocumentBuilder()
					.parse(new File(pathToConfigFile));
			config.normalize();
		} catch (Exception e) {
			System.exit(0);
			e.printStackTrace();
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
		return ((null == env) || env.isEmpty()) ? "prod" : env;
		
	}
}
