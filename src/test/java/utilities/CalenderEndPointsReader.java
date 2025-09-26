package utilities;

import java.io.InputStream;
import java.util.Properties;

public class CalenderEndPointsReader {
	private static Properties properties = new Properties();
	static {
		try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("calender.properties")) {

			if (input == null) {
				throw new RuntimeException("calender.properties file not found in resources!");
			}
			properties.load(input);
		} catch (Exception e) {
			throw new RuntimeException("Failed to load calender.properties", e);
		}
	}

	public static String get(String key) {
		return properties.getProperty(key);
	}
}
