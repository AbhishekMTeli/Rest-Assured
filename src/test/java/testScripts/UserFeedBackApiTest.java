package testScripts;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import endpoints.ExamEndPoints;
import io.restassured.response.Response;
import utilities.ApiUtils;

public class UserFeedBackApiTest extends BaseTest {

	@Test(description = "Verify User Feedback API with valid staff number and valid authorization", retryAnalyzer = utilities.RetryAnalyzer.class)
	public void verifyUserFeedback_ValidStaffNumberWithAuth() {
		Response response = ExamEndPoints.userFeedback(true, true, "13355", null, false, null);
		ApiUtils.validateStatusCode(response, 200);
		ApiUtils.validateNotNull(response, "[0].assessmentGradingId");
		ApiUtils.validateNotNull(response, "[0].pubAssessmentId");
		ApiUtils.validateNotNull(response, "[0].assessmentName");
		ApiUtils.validateNotNull(response, "[0].courseId");
		ApiUtils.validateResponseTime(response, 3000);
	}

	@Test(description = "Verify User Feedback API returns 401 Unauthorized when Authorization header is missing")
	public void verifyUserFeedback_MissingAuthorization() {
		Response response = ExamEndPoints.userFeedback(false, true, "13355", null, false, null);
		ApiUtils.validateStatusCode(response, 401);
		AssertJUnit.assertTrue(response.getContentType().contains("text/html"));
		String body = response.getBody().asString();
		AssertJUnit.assertTrue(body.contains("Unauthorized"));
	}

	@Test(description = "Verify User Feedback API returns 401 Unauthorized for invalid token")
	public void verifyUserFeedback_InvalidAuthorizationToken() {
		Response response = ExamEndPoints.userFeedback(true, true, "13355", "invalidToken123", false, null);
		ApiUtils.validateStatusCode(response, 401);
		AssertJUnit.assertTrue(response.getContentType().contains("text/html"));
		String body = response.getBody().asString();
		AssertJUnit.assertTrue(body.contains("Unauthorized"));
	}

	@Test(description = "Verify User Feedback API returns 400 Bad Request Error when staff number parameter is missing")
	public void verifyUserFeedback_MissingStaffNumberParam() {
		Response response = ExamEndPoints.userFeedback(true, false, null, null, false, null);
		ApiUtils.validateStatusCode(response, 400);
	}

	@Test(description = "Verify User Feedback API returns empty list for invalid staff number parameter")
	public void verifyUserFeedback_InvalidStaffNumberParam() {
		Response response = ExamEndPoints.userFeedback(true, true, "133557799", null, false, null);
		ApiUtils.validateStatusCode(response, 200);
		ApiUtils.validateEmptyList(response, "$");
	}

	@Test(description = "Verify User Feedback API with optional courseId parameter included")
	public void verifyUserFeedback_WithOptionalCourseId() {
		Response response = ExamEndPoints.userFeedback(true, true, "13355", null, true, "12422");
		ApiUtils.validateStatusCode(response, 200);
		ApiUtils.validateNotNull(response, "[0].assessmentGradingId");
		ApiUtils.validateNotNull(response, "[0].pubAssessmentId");
		ApiUtils.validateNotNull(response, "[0].assessmentName");
		ApiUtils.validateNotNull(response, "[0].courseId");
		ApiUtils.validateResponseTime(response, 1000);
	}

	@Test(description = "Verify User Feedback API handles boundary staff number values")
	public void verifyUserFeedback_BoundaryStaffNumber() {
		// Zero staff number
		Response zeroResponse = ExamEndPoints.userFeedback(true, true, "0", null, false, null);
		ApiUtils.validateStatusCode(zeroResponse, 200);

		// Extremely large staff number
		Response largeResponse = ExamEndPoints.userFeedback(true, true, "999999999999999999", null, false, null);
		ApiUtils.validateStatusCode(largeResponse, 200); // Or 200 with empty list depending on API
	}

	@Test(description = "Verify User Feedback API response schema is valid (pseudo code)")
	public void verifyUserFeedback_ResponseSchema() {
		Response response = ExamEndPoints.userFeedback(true, true, "13355", null, false, null);
		ApiUtils.validateStatusCode(response, 200);

		// Use your JSON schema validation method here, e.g.:
		// response.then().assertThat().body(matchesJsonSchemaInClasspath("userFeedbackSchema.json"));
	}

	@Test(description = "Verify User Feedback API returns consistent error messages")
	public void verifyUserFeedback_ErrorMessageConsistency() {
		Response response = ExamEndPoints.userFeedback(true, false, null, null, false, null);
		ApiUtils.validateStatusCode(response, 500);
		String body = response.getBody().asString();
		AssertJUnit.assertTrue(body.toLowerCase().contains("required request parameter 'staffNumber'"));

		// Add more assertions for other error cases if needed
	}
}
