package utilities;

import java.io.InputStream;
import java.util.Properties;

public class ReferenceMaterialEndPointsReader {
	private static Properties properties = new Properties();
	static {
		try (InputStream input = ReferenceMaterialEndPointsReader.class.getClassLoader()
				.getResourceAsStream("analysis.properties")) {
			if (input == null) {
				throw new RuntimeException("analysis.properties file not found in resources!");
			}
			properties.load(input);
		} catch (Exception e) {
			throw new RuntimeException("Failed to load analysis.properties", e);
		}
	}

	public static String get(String key) {
		return properties.getProperty(key);
	}
}
