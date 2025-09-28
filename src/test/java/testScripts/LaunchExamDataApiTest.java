package testScripts;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import endpoints.ExamEndPoints;
import io.restassured.response.Response;
import utilities.ApiUtils;

public class LaunchExamDataApiTest extends BaseTest {

	@Test(description = "Verify Launch Exam API - Validate key response fields", retryAnalyzer = utilities.RetryAnalyzer.class)
	public void verifyLaunchExamData_ValidStaffNumber() {
		Response response = ExamEndPoints.launchExam(true, null, true);

		ApiUtils.validateStatusCode(response, 200);
		ApiUtils.validateNotNull(response, "pubAssessmentId");
		ApiUtils.validateNotNull(response, "assessmentName");
		ApiUtils.validateNotNull(response, "assessmentId");
		ApiUtils.validateNotNull(response, "courseModel.courseId");
		ApiUtils.validateNotNull(response, "courseModel.courseTitle");
		ApiUtils.validateNotNull(response, "courseModel.courseType");
		Assert.assertEquals(ApiUtils.getValue(response, "courseModel.courseType"), "Course",
				"Field courseType does not match expected value");
		ApiUtils.validateNotNull(response, "sectionList");
		ApiUtils.validateNotNull(response, "sectionList[0].pubSectionId");
		ApiUtils.validateNotNull(response, "sectionList[0].pointValueForQuestion");
		ApiUtils.validateNotNull(response, "sectionList[0].negativePointValue");
		ApiUtils.validateNotNull(response, "sectionList[0].questionsList");
		ApiUtils.validateNotNull(response, "sectionList[0].questionsList[0].pubQuestionId");
		ApiUtils.validateNotNull(response, "sectionList[0].questionsList[0].scorePoints");
		ApiUtils.validateNotNull(response, "sectionList[0].questionsList[0].qtmodel.id");
		ApiUtils.validateNotNull(response, "sectionList[0].questionsList[0].qtmodel.questionType");
		ApiUtils.validateNotNull(response, "sectionList[0].questionsList[0].answerList");
		ApiUtils.validateNotNull(response, "sectionList[0].questionsList[0].answerList[0].pubAnswerId");
		ApiUtils.validateNotNull(response, "sectionList[0].questionsList[0].answerList[0].label");
		ApiUtils.validateNotNull(response, "sectionList[0].questionsList[0].answerList[0].is_correct");
		ApiUtils.validateNotNull(response, "sectionList[0].questionsList[0].answerList[0].score");
	}

	@Test(description = "Verify Launch Exam API returns 401 Unauthorized for invalid token")
	public void verifyLaunchExam_InvalidAuthorizationToken() {
		Response response = ExamEndPoints.launchExam(true, "Authorization Token", true);
		ApiUtils.validateStatusCode(response, 401);
		Assert.assertTrue(response.getContentType().contains("text/html"), "Content-Type does not contain 'text/html'");
		String body = response.getBody().asString();
		Assert.assertTrue(body.contains("HTTP Status 401 – Unauthorized"),
				"Response body does not contain 'HTTP Status 401 – Unauthorized'");
	}

	@Test(description = "Verify Launch Exam API returns 401 Unauthorized without token")
	public void verifyLaunchExam_WithoutAuthorizationToken() {
		Response response = ExamEndPoints.launchExam(false, null, true);
		ApiUtils.validateStatusCode(response, 401);
		Assert.assertTrue(response.getContentType().contains("text/html"), "Content-Type does not contain 'text/html'");
		String body = response.getBody().asString();
		Assert.assertTrue(body.contains("HTTP Status 401 – Unauthorized"),
				"Response body does not contain 'HTTP Status 401 – Unauthorized'");
	}

	@Test(description = "Verify Launch Exam API without parameter")
	public void verifyLaunchExam_missingPathParams() {
		Response response = ExamEndPoints.launchExam(true, null, false);
		ApiUtils.validateStatusCode(response, 500);
		Assert.assertTrue(response.getContentType().contains("text/html"), "Content-Type does not contain 'text/html'");
		String body = response.getBody().asString();
		Assert.assertTrue(body.contains("Expected error message here"),
				"Response body does not contain expected error message");
	}

	@Test(description = "Verify Launch Exam API returns 401 for invalid path parameter format")
	public void verifyLaunchExam_InvalidPathParamFormat() {
		String invalidPath = "/invalid123";
		Response response = given().spec(requestSpecNoAuth).when().get("/exam/launchExam" + invalidPath);

		ApiUtils.validateStatusCode(response, 401);
		Assert.assertTrue(response.getContentType().contains("text/html"), "Content-Type does not contain 'text/html'");
		String body = response.getBody().asString();
		Assert.assertTrue(body.toLowerCase().contains("unauthorized"), "Response body does not contain 'Unauthorized'");
	}

	@Test(description = "Verify Launch Exam API handles boundary path parameter values")
	public void verifyLaunchExam_BoundaryPathParam() {
		Response responseZero = given().spec(requestSpecAuth).when().get("/exam/launchExam/0");
		ApiUtils.validateStatusCode(responseZero, 400);

		String largeId = "999999999999999999";
		Response responseLarge = given().spec(requestSpecAuth).when().get("/exam/launchExam/" + largeId);
		ApiUtils.validateStatusCode(responseLarge, 400);
	}

	@Test(description = "Verify Launch Exam API response time is within acceptable limits")
	public void verifyLaunchExam_ResponseTime() {
		Response response = ExamEndPoints.launchExam(true, null, true);
		ApiUtils.validateStatusCode(response, 200);
		long responseTime = response.getTime();
		Assert.assertTrue(responseTime < 2000, "Response time exceeded: " + responseTime + " ms");
	}

	@Test(description = "Verify Launch Exam API response schema compliance (pseudo test)")
	public void verifyLaunchExam_ResponseSchema() {
		Response response = ExamEndPoints.launchExam(true, null, true);
		ApiUtils.validateStatusCode(response, 200);
		// response.then().assertThat().body(matchesJsonSchemaInClasspath("launchExamSchema.json"));
	}

	@Test(description = "Verify consistent error messages for bad requests")
	public void verifyLaunchExam_ErrorMessageConsistency() {
		Response response = ExamEndPoints.launchExam(true, null, false);
		ApiUtils.validateStatusCode(response, 400);
		String body = response.getBody().asString();
		Assert.assertTrue(body.toLowerCase().contains("bad request") || body.toLowerCase().contains("missing"),
				"Error message does not contain 'bad request' or 'missing'");
	}
}
