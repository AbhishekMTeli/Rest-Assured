package testScripts;

import org.testng.annotations.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.Test;

import base.AdminBaseTest;
import endpoints.AdminAnalysisEndPoints;
import io.restassured.response.Response;
import utilities.ApiUtils;

public class AssessmentAnalysisTest extends AdminBaseTest {
	@Test(description = "Verify Assessment Analysis API with valid authentication and parameters returns 200 OK and valid fields")
	public void verifyCourseUtilizationSummary_WithValidAuthAndParams_ShouldReturn200AndValidData() {
		Response response = AdminAnalysisEndPoints.assessmentAnalysis(true, null, true, null);
		ApiUtils.validateStatusCode(response, 200);
		ApiUtils.validateFieldExists(response, "[0].ASSESSMENT_NAME");
		ApiUtils.validateFieldExists(response, "[0].Critical[0].marks_scored");
		ApiUtils.validateFieldExists(response, "[0].Critical[0].section_score");
		ApiUtils.validateFieldExists(response, "[0].Critical[0].userid");
		ApiUtils.validateFieldExists(response, "[0].Critical[0].username");
		ApiUtils.validateFieldExists(response, "[0].Excellent");
		ApiUtils.validateFieldExists(response, "[0].Good");
		ApiUtils.validateFieldExists(response, "[0].SECTION_TITLE");
		ApiUtils.validateFieldExists(response, "[0].Satisfactory");
		ApiUtils.validateFieldExists(response, "[0].TOPIC");
		ApiUtils.validateFieldExists(response, "[0].TOTAL_USERS");
		ApiUtils.validateFieldExists(response, "[0].Weak");
	}

	@Test(description = "Verify Assessment Analysis API returns 401 Unauthorized for invalid authentication token")
	public void verifyAssessmentAnalysis_WithInvalidAuth_ShouldReturn401() {
		Response response = AdminAnalysisEndPoints.assessmentAnalysis(true, "ovverride token", true, null);
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify Assessment Analysis API returns 401 Unauthorized when no authentication is provided")
	public void verifyAssessmentAnalysis_WithoutAuth_ShouldReturn401() {
		Response response = AdminAnalysisEndPoints.assessmentAnalysis(false, null, true, null);
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify Assessment Analysis API response matches the expected JSON schema")
	public void verifyAssessmentAnalysis_ResponseMatchesJsonSchema() {
		Response response = AdminAnalysisEndPoints.assessmentAnalysis(true, null, true, null);
		response.then().body(matchesJsonSchemaInClasspath("examFeedbackSchema.json"));
	}

	@Test(description = "Verify Assessment Analysis API returns 401 Unauthorized for expired token")
	public void verifyAssessmentAnalysis_WithExpiredToken_ShouldReturn401() {
		String expiredToken = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJuaXRoaW4ucEBhbnVncmFoYS5jbyIsImlzcyI6InlvdXItYXBwIiwiaWF0IjoxNzU3MzI2MTkzLCJleHAiOjE3NTczMjcwOTMsInR5cGUiOiJhY2Nlc3MifQ.OT1BRSAtjFt-E3EGdLDiyUhr42SDBR4Ao8lRJhK-7yP4xSpHW7RMasvtaj5eKZCnJrWgaDjh5koZGP7aDqXsRq1iCcqoqjnxSZ44_bNLjOl6xJvfHLafk4e79qpoYbJZlFqZ3bgGqq3mkBrLVVI1789URkYSkQPY_0UZKZwG1D2cF7wR6_FMfUnevp9Ra1evnyFYMHxSsQTFuN8vR0-Bxm2dQ-z6REXV8NlhD8UzNuVAieN2QEXUEka01bn9VYOX7Ur0_0BpqmaUO5xY4z489C__He3d6ypryGxGU288CstjBi8CtT0FzlYgTJh73EU9_jGjgu_o7RAF-iFxGjek5g";
		Response response = AdminAnalysisEndPoints.assessmentAnalysis(true, expiredToken, true, null);
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify Assessment Analysis API returns 400 bad request")
	public void verifyAssessmentAnalysis_WithmissingAssessmentId_ShouldReturn400() {
		Response response = AdminAnalysisEndPoints.assessmentAnalysis(true, null, false, null);
		ApiUtils.validateStatusCode(response, 400);
	}

	@Test(description = "Verify Assessment Analysis API returns 404 not found")
	public void verifyAssessmentAnalysis_WrongAssessmentIdd_ShouldReturn404() {
		Response response = AdminAnalysisEndPoints.assessmentAnalysis(true, null, true, "1168");
		ApiUtils.validateStatusCode(response, 404);
	}
}
