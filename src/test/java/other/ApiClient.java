package other;

import io.restassured.RestAssured;

public class ApiClient {

	public static String get(String url) {
		return RestAssured.get(url).asString();
	}

	public static String getStatus(String url) {
		return RestAssured.get(url).asString();
	}
}
