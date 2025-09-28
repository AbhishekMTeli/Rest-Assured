package testScripts;

import org.testng.annotations.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.Test;

import base.AdminBaseTest;
import endpoints.AdminAnalysisEndPoints;
import io.restassured.response.Response;
import utilities.ApiUtils;

public class IncompleteAssessmentsTest extends AdminBaseTest {
	@Test(description = "Verify Incomplete Assessments API with valid authentication and parameters returns 200 OK and valid fields")
	public void verifyIncompleteAssessments_WithValidAuthAndParams_ShouldReturn200AndValidData() {
		Response response = AdminAnalysisEndPoints.incomleteAssessments(true, null);
		ApiUtils.validateStatusCode(response, 200);
		ApiUtils.validateFieldExists(response, "[0].ASSESSMENT_NAME");
		ApiUtils.validateFieldExists(response, "[0].COURSE_ID");
		ApiUtils.validateFieldExists(response, "[0].COURSE_TITLE");
		ApiUtils.validateFieldExists(response, "[0].INCOMPLETE[0].NAME");
		ApiUtils.validateFieldExists(response, "[0].INCOMPLETE[0].NUMBER");
	}

	@Test(description = "Verify Incomplete Assessments API returns 401 Unauthorized for invalid authentication token")
	public void verifyIncompleteAssessments_WithInvalidAuth_ShouldReturn401() {
		Response response = AdminAnalysisEndPoints.incomleteAssessments(true, "ovverride token");
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify Incomplete Assessments API returns 401 Unauthorized when no authentication is provided")
	public void verifyIncompleteAssessments_WithoutAuth_ShouldReturn401() {
		Response response = AdminAnalysisEndPoints.incomleteAssessments(false, null);
		ApiUtils.validateStatusCode(response, 401);
	}

	@Test(description = "Verify Incomplete Assessments API response matches the expected JSON schema")
	public void verifyIncompleteAssessments_ResponseMatchesJsonSchema() {
		Response response = AdminAnalysisEndPoints.incomleteAssessments(true, null);
		response.then().body(matchesJsonSchemaInClasspath("examFeedbackSchema.json"));
	}

	@Test(description = "Verify Incomplete Assessments API returns 401 Unauthorized for expired token")
	public void verifyIncompleteAssessments_WithExpiredToken_ShouldReturn401() {
		String expiredToken = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJuaXRoaW4ucEBhbnVncmFoYS5jbyIsImlzcyI6InlvdXItYXBwIiwiaWF0IjoxNzU3MzI2MTkzLCJleHAiOjE3NTczMjcwOTMsInR5cGUiOiJhY2Nlc3MifQ.OT1BRSAtjFt-E3EGdLDiyUhr42SDBR4Ao8lRJhK-7yP4xSpHW7RMasvtaj5eKZCnJrWgaDjh5koZGP7aDqXsRq1iCcqoqjnxSZ44_bNLjOl6xJvfHLafk4e79qpoYbJZlFqZ3bgGqq3mkBrLVVI1789URkYSkQPY_0UZKZwG1D2cF7wR6_FMfUnevp9Ra1evnyFYMHxSsQTFuN8vR0-Bxm2dQ-z6REXV8NlhD8UzNuVAieN2QEXUEka01bn9VYOX7Ur0_0BpqmaUO5xY4z489C__He3d6ypryGxGU288CstjBi8CtT0FzlYgTJh73EU9_jGjgu_o7RAF-iFxGjek5g";
		Response response = AdminAnalysisEndPoints.incomleteAssessments(true, expiredToken);
		ApiUtils.validateStatusCode(response, 401);
	}
}
