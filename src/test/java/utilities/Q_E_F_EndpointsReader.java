package utilities;

import java.io.InputStream;
import java.util.Properties;

public class Q_E_F_EndpointsReader {
	private static Properties properties = new Properties();

	static {
		try (InputStream input = ExamEndPointsReader.class.getClassLoader().getResourceAsStream("q_e_f.properties")) {

			if (input == null) {
				throw new RuntimeException("q_e_f.properties file not found in resources!");
			}
			properties.load(input);
		} catch (Exception e) {
			throw new RuntimeException("Failed to load q_e_f.properties", e);
		}
	}

	public static String get(String key) {
		return properties.getProperty(key);
	}
}
