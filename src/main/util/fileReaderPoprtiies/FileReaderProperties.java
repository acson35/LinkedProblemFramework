package main.util.fileReaderPoprtiies;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class FileReaderProperties {
	
	private Properties props;
	
	public FileReaderProperties(String Filename) {
		try {
			InputStream inputStream = new FileInputStream(Filename);
			props = new Properties();
			props.load(inputStream);
			inputStream.close();
		}catch (Exception e) {
			System.err.println("ERROR");
			e.printStackTrace();
		}
	}
	
	public Properties getProperties() {
		return props;
	}
	
	public String getPropertyValue(String key) {
		return getProperties().getProperty(key);
	}
	
	public String getPropertyStringValue(String key, String defaultValue) {
		String value = getPropertyValue(key);
		return value != null ? value : defaultValue;
	}
	
	public int getPropertyIntValue(String key, int defaultValue) {
		try {
			return Integer.parseInt(getPropertyValue(key));
		}catch(Exception ex) {
			return defaultValue;
		}
	}
}
