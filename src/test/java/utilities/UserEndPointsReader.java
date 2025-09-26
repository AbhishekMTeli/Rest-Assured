package utilities;

import java.io.InputStream;
import java.util.Properties;

public class UserEndPointsReader {
	private static Properties properties = new Properties();

	static {
		try (InputStream input = UserEndPointsReader.class.getClassLoader().getResourceAsStream("user.properties")) {

			if (input == null) {
				throw new RuntimeException("user.properties file not found in resources!");
			}
			properties.load(input);
		} catch (Exception e) {
			throw new RuntimeException("Failed to load user.properties", e);
		}
	}

	public static String get(String key) {
		return properties.getProperty(key);
	}
}
