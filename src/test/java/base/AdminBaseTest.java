package base;

import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.ConfigReader;

public class AdminBaseTest {

	protected static RequestSpecification requestSpecAuth;
	protected static RequestSpecification requestSpecNoAuth;
	private static String accessToken;

	private static final String API_KEY = "LearnOn@123";

	@BeforeClass(alwaysRun = true)
	public void setUp() {
		// Load base config
		RestAssured.baseURI = ConfigReader.get("adminBaseURI");
		RestAssured.basePath = ConfigReader.get("adminBasePath");

		// Initialize No-Auth Spec first (important: generate token uses this)
		requestSpecNoAuth = new RequestSpecBuilder().addHeader("Content-Type", "application/json")
				.addHeader("X-API-KEY", API_KEY).setBaseUri(RestAssured.baseURI).setBasePath(RestAssured.basePath)
				.build();

		// Generate token only once per suite
		if (accessToken == null || accessToken.isEmpty()) {
			accessToken = generateAdminAccessToken();
		}

		// Initialize Auth Spec with Bearer token
		requestSpecAuth = new RequestSpecBuilder().addHeader("Authorization", "Bearer " + accessToken)
				.addHeader("Content-Type", "application/json").addHeader("X-API-KEY", API_KEY)
				.setBaseUri(RestAssured.baseURI).setBasePath(RestAssured.basePath).build();
	}

	private String generateAdminAccessToken() {
		// Call the auth endpoint without body (since Postman showed empty body)
		Response response = given().spec(requestSpecNoAuth).when().post("/authenticateAnalysis");

		if (response.statusCode() != 200) {
			throw new RuntimeException("❌ Token generation failed: " + response.asString());
		}

		String token = response.jsonPath().getString("access_token");
		System.out.println("✅ Generated Access Token: " + token);
		return token;
	}
}
