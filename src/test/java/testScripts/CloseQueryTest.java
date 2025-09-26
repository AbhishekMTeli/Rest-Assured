package testScripts;

import org.testng.annotations.Test;

import base.BaseTest;
import endpoints.Q_E_F_Endpoints;
import io.restassured.response.Response;
import utilities.ApiUtils;

public class CloseQueryTest extends BaseTest {

	@Test(description = "Positive: Validate closeQuery API with valid queryId and userRole using authentication, expect 200 OK and success message")
	public void testCloseQueryWithValidParams() {
		Response response = Q_E_F_Endpoints.closeQuery(true, null, true, "Administrator", true, null);
		ApiUtils.logResponse(response);
		ApiUtils.validateStatusCode(response, 200);
		String expectedMessage = "Query closed Successfully";
		String responseBody = response.asString();
		assert responseBody.contains(expectedMessage) : "Expected success message not found";
	}

	@Test(description = "Negative: Validate closeQuery API returns 400 Bad Request when queryId parameter is missing")
	public void testCloseQueryMissingQueryId() {
		Response response = Q_E_F_Endpoints.closeQuery(true, null, true, "Administrator", false, null);
		ApiUtils.logResponse(response);
		ApiUtils.validateStatusCode(response, 400);
		ApiUtils.validateFieldExists(response, "error");
	}

	@Test(description = "Negative: Validate closeQuery API returns 400 Bad Request for invalid (non-numeric) queryId")
	public void testCloseQueryInvalidQueryId() {
		Response response = Q_E_F_Endpoints.closeQuery(true, null, true, "Administrator", true, "abc");
		ApiUtils.logResponse(response);
		ApiUtils.validateStatusCode(response, 400);
		ApiUtils.validateFieldExists(response, "error");
	}

	@Test(description = "Negative: Validate closeQuery API returns 401 Unauthorized when no authentication token is provided")
	public void testCloseQueryNoAuthentication() {
		Response response = Q_E_F_Endpoints.closeQuery(false, null, true, "Administrator", true, "106");
		ApiUtils.logResponse(response);
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Negative: Validate closeQuery API returns 401 Unauthorized when an invalid or expired token is used")
	public void testCloseQueryInvalidToken() {
		Response response = Q_E_F_Endpoints.closeQuery(true, "invalid_or_expired_token", true, "Administrator", true,
				"106");
		ApiUtils.logResponse(response);
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Security: Validate API prevents SQL injection in queryId parameter and returns 400 Bad Request")
	public void testCloseQuerySqlInjection() {
		Response response = Q_E_F_Endpoints.closeQuery(true, null, true, "Administrator", true, "' OR '1'='1");
		ApiUtils.logResponse(response);
		ApiUtils.validateStatusCode(response, 400);
		ApiUtils.validateFieldExists(response, "error");
	}

	@Test(description = "Schema: Validate the closeQuery API response JSON matches the expected schema")
	public void testCloseQueryResponseSchema() {
		Response response = Q_E_F_Endpoints.closeQuery(true, null, true, "Administrator", true, "106");
		ApiUtils.validateStatusCode(response, 200);
		ApiUtils.validateJsonSchema(response, "schemas/closeQuerySchema.json");
	}
}
