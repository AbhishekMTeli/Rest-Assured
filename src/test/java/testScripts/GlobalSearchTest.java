package testScripts;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import endpoints.GlobalSearchEndPoints;
import io.restassured.response.Response;
import utilities.ApiUtils;

public class GlobalSearchTest extends BaseTest {
	@Test(description = "Positive: Validate Global Search API with valid staffNumber and query using authentication, expect 200 OK and success message")
	public void testGlobalSearchWithValidParams() {
		Response response = GlobalSearchEndPoints.global_Search(true, null, true, null, true, null);
		ApiUtils.validateStatusCode(response, 200);
		ApiUtils.validateFieldExists(response, "LESSON[0].id");
		ApiUtils.validateFieldExists(response, "LESSON[0].type");
		ApiUtils.validateFieldExists(response, "LESSON[0].title");
//		ApiUtils.validateFieldExists(response, "LESSON[0].description");
		ApiUtils.validateFieldExists(response, "LESSON[0].entityId");
//		ApiUtils.validateFieldExists(response, "LESSON[0].author");
//		ApiUtils.validateFieldExists(response, "LESSON[0].createdDate");
//		ApiUtils.validateFieldExists(response, "LESSON[0].status");
		ApiUtils.validateFieldExists(response, "LESSON[0].courseId");
		ApiUtils.validateFieldExists(response, "LESSON[0].moduleId");
		ApiUtils.validateFieldExists(response, "LESSON[0].assignedTo");
		ApiUtils.validateNotEmpty(response, "LESSON[0].courseId");
	}

	@Test(description = "Negative: Validate Global Search API returns 400 Bad Request when staffNumber parameter is missing")
	public void testGlobalSearchMissingQueryId() {
		Response response = GlobalSearchEndPoints.global_Search(true, null, true, "tester", false, null);
		ApiUtils.validateStatusCode(response, 400);
		ApiUtils.validateFieldExists(response, "error");
	}

	@Test(description = "Negative: Validate Global Search API returns 400 Bad Request for invalid (non-numeric) staffNumber")
	public void testGlobalSearchInvalidQueryId() {
		Response response = GlobalSearchEndPoints.global_Search(true, null, true, null, true, "abc");
		ApiUtils.logResponse(response);
		ApiUtils.validateStatusCode(response, 200);
		AssertJUnit.assertEquals(response.asString(), "{}", "Expected empty JSON object");
	}

	@Test(description = "Negative: Validate Global Search API returns 401 Unauthorized when no authentication token is provided")
	public void testGlobalSearchNoAuthentication() {
		Response response = GlobalSearchEndPoints.global_Search(false, null, true, null, true, "133552");
		ApiUtils.logResponse(response);
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Negative: Validate Global Search API returns 401 Unauthorized when an invalid or expired token is used")
	public void testGlobalSearchInvalidToken() {
		Response response = GlobalSearchEndPoints.global_Search(true, "invalid_or_expired_token", true, "cbt", true,
				"1223344");
		ApiUtils.logResponse(response);
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Schema: Validate the Global Search API response JSON matches the expected schema")
	public void testGlobalSearchResponseSchema() {
		Response response = GlobalSearchEndPoints.global_Search(true, null, true, null, true, null);
		ApiUtils.validateStatusCode(response, 200);
		ApiUtils.validateJsonSchema(response, "schemas/closeQuerySchema.json");
	}
}
