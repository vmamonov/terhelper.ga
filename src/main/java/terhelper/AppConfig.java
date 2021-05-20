package terhelper;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class AppConfig {
	private static AppConfig self;
	private String pathToConfigFile = Env.APP_PATH_ROOT + "terhelper/app-config.xml"; 
	private Document config;
	
	public static AppConfig getInstance() throws SAXException, IOException, ParserConfigurationException {
		if (null == self) {
			self = new AppConfig();
		}	
		return self;
	}
	
	private AppConfig() throws SAXException, IOException, ParserConfigurationException {
		config = DocumentBuilderFactory
				.newInstance()
				.newDocumentBuilder()
				.parse(new File(pathToConfigFile));
		config.normalize();
	}
	
	public String getUserIdForApi() {
		return config.getElementsByTagName("userid").item(0).getTextContent();
	}
	
	public String getSecretForApi() {
		return config.getElementsByTagName("secret").item(0).getTextContent();
	}
}
