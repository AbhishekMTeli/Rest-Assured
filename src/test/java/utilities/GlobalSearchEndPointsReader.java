package utilities;

import java.io.InputStream;
import java.util.Properties;

public class GlobalSearchEndPointsReader {
	private static Properties properties = new Properties();

	static {
		try (InputStream input = ExamEndPointsReader.class.getClassLoader().getResourceAsStream("search.properties")) {

			if (input == null) {
				throw new RuntimeException("search.properties file not found in resources!");
			}
			properties.load(input);
		} catch (Exception e) {
			throw new RuntimeException("Failed to load search.properties", e);
		}
	}

	public static String get(String key) {
		return properties.getProperty(key);
	}
}
