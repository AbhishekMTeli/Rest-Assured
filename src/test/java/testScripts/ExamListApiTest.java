package testScripts;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import endpoints.ExamEndPoints;
import io.restassured.response.Response;
import utilities.ApiUtils;

public class ExamListApiTest extends BaseTest {

	@Test(description = "Verify examList API returns data with valid authorization and required params")
	public void verifyExamList_ValidAuthAndParams() {
		Response response = ExamEndPoints.examList(true, null, true, "13355", true, "12422");
		ApiUtils.validateStatusCode(response, 200);

		// Validate course array not empty
		ApiUtils.validateNotNull(response, "$[0].courseId");
		ApiUtils.validateNotNull(response, "$[0].courseName");
		ApiUtils.validateNotNull(response, "$[0].assessments");

		// Validate first assessment details
		ApiUtils.validateNotNull(response, "$[0].assessments[0].pubAssessmentId");
		ApiUtils.validateNotNull(response, "$[0].assessments[0].assessmentName");
		ApiUtils.validateNotNull(response, "$[0].assessments[0].dueDate");
		ApiUtils.validateNotNull(response, "$[0].assessments[0].timeLimit");
		ApiUtils.validateNotNull(response, "$[0].assessments[0].numQuestionsDrawn");
		ApiUtils.validateNotNull(response, "$[0].assessments[0].gradingCount");
		ApiUtils.validateNotNull(response, "$[0].assessments[0].submissionsAllowed");

		ApiUtils.validateResponseTime(response, 3000);
	}

	@Test(description = "Verify examList API returns 401 Unauthorized when auth missing")
	public void verifyExamList_MissingAuthorization() {
		Response response = ExamEndPoints.examList(false, null, true, "13355", true, "12422");
		ApiUtils.validateStatusCode(response, 401);
		Assert.assertTrue(response.getContentType().contains("text/html"), "Content-Type missing 'text/html'");
		String body = response.getBody().asString();
		Assert.assertTrue(body.contains("Unauthorized"), "Response body missing 'Unauthorized'");
	}

	@Test(description = "Verify examList API returns 401 Unauthorized for invalid token")
	public void verifyExamList_InvalidAuthorizationToken() {
		Response response = ExamEndPoints.examList(true, "invalidToken123", true, "13355", true, "12422");
		ApiUtils.validateStatusCode(response, 401);
		Assert.assertTrue(response.getContentType().contains("text/html"), "Content-Type missing 'text/html'");
		String body = response.getBody().asString();
		Assert.assertTrue(body.contains("Unauthorized"), "Response body missing 'Unauthorized'");
	}

	@Test(description = "Verify examList API returns 400 Bad Request when staffNumber missing but marked mandatory")
	public void verifyExamList_MissingStaffNumber() {
		Response response = ExamEndPoints.examList(true, null, false, null, true, "12422");
		ApiUtils.validateStatusCode(response, 400);
		// Optionally validate message or error code
	}

	@Test(description = "Verify examList API returns 400 Bad Request when courseId missing but marked mandatory")
	public void verifyExamList_MissingCourseId() {
		Response response = ExamEndPoints.examList(true, null, true, "13355", false, null);
		ApiUtils.validateStatusCode(response, 400);
		// Optionally validate message or error code
	}

	@Test(description = "Verify examList API returns empty list for invalid staff number or courseId")
	public void verifyExamList_InvalidStaffOrCourseId() {
		Response response = ExamEndPoints.examList(true, null, true, "999999", true, "999999");
		ApiUtils.validateStatusCode(response, 200);
		ApiUtils.validateEmptyList(response, "$");
	}

	@Test(description = "Verify examList API response time within acceptable limit")
	public void verifyExamList_ResponseTime() {
		Response response = ExamEndPoints.examList(true, null, true, "13355", true, "12422");
		ApiUtils.validateStatusCode(response, 200);
		long responseTime = response.getTime();
		Assert.assertTrue(responseTime < 2000, "Response time exceeded: " + responseTime + " ms");
	}

	@Test(description = "Verify examList API response complies with JSON schema")
	public void verifyExamList_ResponseSchema() {
		Response response = ExamEndPoints.examList(true, null, true, "13355", true, "12422");
		ApiUtils.validateStatusCode(response, 200);
		response.then().assertThat().body(matchesJsonSchemaInClasspath("examListSchema.json"));
	}

}
