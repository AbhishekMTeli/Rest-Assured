package testScripts;

import org.testng.annotations.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.Test;

import base.AdminBaseTest;
import endpoints.AdminAnalysisEndPoints;
import io.restassured.response.Response;
import utilities.ApiUtils;

public class CourseUtilizationSummaryTest extends AdminBaseTest {
	@Test(description = "Verify Course Utilization Summary API with valid authentication and parameters returns 200 OK and valid fields")
	public void verifyCourseUtilizationSummary_WithValidAuthAndParams_ShouldReturn200AndValidData() {
		Response response = AdminAnalysisEndPoints.courseUtilizationSummary(true, null, true, null);
		ApiUtils.validateStatusCode(response, 200);
		ApiUtils.validateFieldExists(response, "ASSESSMENTS[0].ASSESSMENTGRADINGID");
		ApiUtils.validateNotNull(response, "ASSESSMENTS[0].ASSESSMENT_ID");
		ApiUtils.validateNotNull(response, "ASSESSMENTS[0].ASSESSMENT_NAME");
		ApiUtils.validateFieldExists(response, "ASSESSMENTS[0].DATE_OF_COMPLETION");
		ApiUtils.validateNotNull(response, "ASSESSMENTS[0].PASS_PERCENT");
		ApiUtils.validateNotNull(response, "ASSESSMENTS[0].PUB_ASSESSMENT_ID");
		ApiUtils.validateFieldExists(response, "ASSESSMENTS[0].SCORE_PERCENT");
		ApiUtils.validateNotNull(response, "ASSESSMENTS[0].STATUS");
		ApiUtils.validateFieldExists(response, "ASSESSMENTS[0].TOTALAUTOSCORE");
		ApiUtils.validateFieldExists(response, "ASSESSMENTS[0].USERSCORE");
		ApiUtils.validateNotNull(response, "ASSESSMENTS[0].USER_ID");
		ApiUtils.validateNotNull(response, "ASSESSMENTS[0].USER_NAME");
	}

	@Test(description = "Verify Course Utilization Summary API returns 401 Unauthorized for invalid authentication token")
	public void verifyCourseUtilizationSummary_WithInvalidAuth_ShouldReturn401() {
		Response response = AdminAnalysisEndPoints.courseUtilizationSummary(true, "ovverride token", true, null);
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify Course Utilization Summary API returns 401 Unauthorized when no authentication is provided")
	public void verifyCourseUtilizationSummary_WithoutAuth_ShouldReturn401() {
		Response response = AdminAnalysisEndPoints.courseUtilizationSummary(false, null, true, null);
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify Course Utilization Summary API response matches the expected JSON schema")
	public void verifyCourseUtilizationSummary_ResponseMatchesJsonSchema() {
		Response response = AdminAnalysisEndPoints.courseUtilizationSummary(true, null, true, null);
		response.then().body(matchesJsonSchemaInClasspath("examFeedbackSchema.json"));
	}

	@Test(description = "Verify Course Utilization Summary API returns 401 Unauthorized for expired token")
	public void verifyCourseUtilizationSummary_WithExpiredToken_ShouldReturn401() {
		String expiredToken = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJuaXRoaW4ucEBhbnVncmFoYS5jbyIsImlzcyI6InlvdXItYXBwIiwiaWF0IjoxNzU3MzI2MTkzLCJleHAiOjE3NTczMjcwOTMsInR5cGUiOiJhY2Nlc3MifQ.OT1BRSAtjFt-E3EGdLDiyUhr42SDBR4Ao8lRJhK-7yP4xSpHW7RMasvtaj5eKZCnJrWgaDjh5koZGP7aDqXsRq1iCcqoqjnxSZ44_bNLjOl6xJvfHLafk4e79qpoYbJZlFqZ3bgGqq3mkBrLVVI1789URkYSkQPY_0UZKZwG1D2cF7wR6_FMfUnevp9Ra1evnyFYMHxSsQTFuN8vR0-Bxm2dQ-z6REXV8NlhD8UzNuVAieN2QEXUEka01bn9VYOX7Ur0_0BpqmaUO5xY4z489C__He3d6ypryGxGU288CstjBi8CtT0FzlYgTJh73EU9_jGjgu_o7RAF-iFxGjek5g";
		Response response = AdminAnalysisEndPoints.courseUtilizationSummary(true, expiredToken, true, null);
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify Course Utilization Summary API returns 400 bad request")
	public void verifyCourseUtilizationSummary_WithmissingCourseId_ShouldReturn400() {
		Response response = AdminAnalysisEndPoints.courseUtilizationSummary(true, null, false, null);
		ApiUtils.validateStatusCode(response, 400);
	}

	@Test(description = "Verify Course Utilization Summary API returns 404 not found")
	public void verifyCourseUtilizationSummary_WrongCourseId_ShouldReturn404() {
		Response response = AdminAnalysisEndPoints.courseUtilizationSummary(true, null, true, "1168");
		ApiUtils.validateStatusCode(response, 404);
	}
}
