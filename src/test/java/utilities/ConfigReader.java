package utilities;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
	private static Properties properties = new Properties();

	static {
		try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {

			if (input == null) {
				throw new RuntimeException("exam.properties file not found in resources!");
			}
			properties.load(input);
		} catch (Exception e) {
			throw new RuntimeException("Failed to load exam.properties", e);
		}
	}

	public static String get(String key) {
		return properties.getProperty(key);
	}
}
