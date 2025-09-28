package testScripts;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import base.BaseTest;
import io.restassured.response.Response;
import utilities.ApiUtils;

public class UpdateNotificationStatusTest extends BaseTest {

	private static final String ENDPOINT = "/seen-notifications";

	/** ✅ 1. Valid request with single notification ID */
	@Test
	public void seenNotification_WithSingleId_ShouldReturn200() {
		String requestBody = "[\"39464\"]";

		Response response = given().spec(requestSpecAuth).body(requestBody).when().post(ENDPOINT);

		ApiUtils.validateStatusCode(response, 200);
		ApiUtils.logResponse(response);
	}

	/** ✅ 2. Valid request with multiple notification IDs */
	@Test
	public void seenNotification_WithMultipleIds_ShouldReturn200() {
		String requestBody = "[\"39464\",\"39465\",\"39466\"]";

		Response response = given().spec(requestSpecAuth).body(requestBody).when().post(ENDPOINT);

		ApiUtils.validateStatusCode(response, 200);
		ApiUtils.logResponse(response);
	}

	/** ✅ 3. Empty request body */
	@Test
	public void seenNotification_WithEmptyArray_ShouldReturn400() {
		String requestBody = "[]";

		Response response = given().spec(requestSpecAuth).body(requestBody).when().post(ENDPOINT);

		ApiUtils.validateStatusCode(response, 400);
		ApiUtils.logResponse(response);
	}

	/** ✅ 4. Invalid request body format */
	@Test
	public void seenNotification_WithInvalidFormat_ShouldReturn400() {
		String requestBody = "\"39464\""; // not array

		Response response = given().spec(requestSpecAuth).body(requestBody).when().post(ENDPOINT);

		ApiUtils.validateStatusCode(response, 400);
		ApiUtils.logResponse(response);
	}

	/** ✅ 5. Invalid notification ID */
	@Test
	public void seenNotification_WithInvalidId_ShouldReturn404Or200Or400() {
		String requestBody = "[\"999999\"]";

		Response response = given().spec(requestSpecAuth).body(requestBody).when().post(ENDPOINT);

		// Some APIs ignore invalid IDs and still return 200. Adjust expectation if
		// needed.
		int status = response.getStatusCode();
		assert (status == 200 || status == 404 || status == 400) : "Unexpected status: " + status;
		ApiUtils.logResponse(response);
	}

	/** ✅ 6. Unauthorized request - missing token */
	@Test
	public void seenNotification_WithoutAuth_ShouldReturn401() {
		String requestBody = "[\"39464\"]";

		Response response = given().spec(requestSpecNoAuth).body(requestBody).when().post(ENDPOINT);

		ApiUtils.validateStatusCode(response, 401);
		ApiUtils.logResponse(response);
	}

	/** ✅ 7. Unauthorized request - invalid token */
	@Test
	public void seenNotification_WithInvalidToken_ShouldReturn401() {
		String requestBody = "[\"39464\"]";

		Response response = given().header("Authorization", "Bearer invalidToken")
				.header("Content-Type", "application/json").body(requestBody).when().post(ENDPOINT);

		ApiUtils.validateStatusCode(response, 401);
		ApiUtils.logResponse(response);
	}

	/** ✅ 8. Wrong method - GET instead of POST */
	@Test
	public void seenNotification_WithGetMethod_ShouldReturn405() {
		Response response = given().spec(requestSpecAuth).when().get(ENDPOINT);

		ApiUtils.validateStatusCode(response, 405);
		ApiUtils.logResponse(response);
	}

	/** ✅ 9. Large payload */
	@Test
	public void seenNotification_WithLargePayload_ShouldHandleGracefully() {
		StringBuilder sb = new StringBuilder("[");
		for (int i = 1; i <= 1000; i++) {
			sb.append("\"").append(30000 + i).append("\"");
			if (i < 1000)
				sb.append(",");
		}
		sb.append("]");

		Response response = given().spec(requestSpecAuth).body(sb.toString()).when().post(ENDPOINT);

		ApiUtils.logResponse(response);
		int status = response.getStatusCode();
		assert (status == 200 || status == 413) : "Unexpected status: " + status;
	}
}
