package base;

import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.RefreshTokenRequest;
import utilities.ConfigReader;

public class BaseTest {

	protected static RequestSpecification requestSpecAuth;
	protected static RequestSpecification requestSpecNoAuth;
	protected static RequestSpecification requestSpecAuthMultipart;
	private static String accessToken;

	@BeforeClass(alwaysRun = true)
	public void setUp() {
		// Load base config
		RestAssured.baseURI = ConfigReader.get("baseURI");
		RestAssured.basePath = ConfigReader.get("basePath");

		// Generate token only once per test suite
		if (accessToken == null || accessToken.isEmpty()) {
			accessToken = generateAccessToken();
		}

		// Auth Spec with Bearer token
		requestSpecAuth = new RequestSpecBuilder().addHeader("Authorization", "Bearer " + accessToken)
				.addHeader("Content-Type", "application/json").setBaseUri(RestAssured.baseURI)
				.setBasePath(RestAssured.basePath).build();

		// No Auth Spec without Authorization header
		requestSpecNoAuth = new RequestSpecBuilder().addHeader("Content-Type", "application/json")
				.setBaseUri(RestAssured.baseURI).setBasePath(RestAssured.basePath).build();
		
		 requestSpecAuthMultipart = new RequestSpecBuilder()
			        .addHeader("Authorization", "Bearer " + accessToken)
			        .setBaseUri(RestAssured.baseURI)
			        .setBasePath(RestAssured.basePath)
			        .build();
	}

	private String generateAccessToken() {
		RefreshTokenRequest requestBody = new RefreshTokenRequest();
		requestBody.setRefresh_token(ConfigReader.get("refreshToken"));
		requestBody.setApp_version(ConfigReader.get("app_version"));

		Response response = given().header("Content-Type", "application/json").body(requestBody)
				.post("/auth/refresh-token");

		if (response.statusCode() != 200) {
			throw new RuntimeException("❌ Token generation failed: " + response.asString());
		}

		return response.jsonPath().getString("access_token");
	}
}
